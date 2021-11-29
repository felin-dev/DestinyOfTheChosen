package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.SkillEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.session.CurrentEnemy;
import game.destinyofthechosen.model.session.CurrentHero;
import game.destinyofthechosen.model.view.*;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EnemyService enemyService;
    private final HeroService heroService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserServiceImpl securityUserService;
    private final ModelMapper modelMapper;

    private CurrentHero currentHero;
    private CurrentEnemy currentEnemy;

    public UserServiceImpl(UserRepository userRepository, EnemyService enemyService, HeroService heroService, UserRoleService userRoleService, PasswordEncoder passwordEncoder, SecurityUserServiceImpl securityUserService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.enemyService = enemyService;
        this.heroService = heroService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.securityUserService = securityUserService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CombatStatusViewModel performAttackOnEnemy(String username) {

        if (currentHero == null) setCurrentHero(username);
        checkIfTheCurrentEntityIsNull(currentEnemy == null, "There is no selected enemy.");

        return attackEnemy(username, currentHero.getBaseAttack(), currentHero.getBaseDefense(), false, false);
    }

    @Override
    @Transactional
    public CombatStatusViewModel castSkillOnEnemy(String username, String skillName) {
        if (!currentEnemy.getIsAlive() || !currentHero.getIsAlive()) return createCombatStatusView();

        SkillViewModel skill = currentHero
                .getSkillList().stream()
                .filter(skillViewModel -> skillViewModel.getSkillName().equals(skillName))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("There is no skill with name: " + skillName));

        if (skill.getCurrentCoolDown() > 0) return createCombatStatusView();
        if (currentHero.getCurrentMana() < skill.getManaRequired()) return createCombatStatusView();
        skill.setCurrentCoolDown(skill.getCoolDown() + 1);
        currentHero.setCurrentMana(currentHero.getCurrentMana() - skill.getManaRequired());

        switch (skill.getType()) {
            case DAMAGE -> attackEnemy(username, currentHero.getBaseAttack() + currentHero.getBaseMagicPower() + skill.getSkillValue(), currentHero.getBaseDefense(), false, false);
            case DEFENSE_BUFF -> attackEnemy(username, currentHero.getBaseAttack(), currentHero.getBaseDefense() + skill.getSkillValue(), false, false);
            case HEAL -> {
                currentHero.setCurrentHealth(Math.min(currentHero.getBaseHealth(), currentHero.getCurrentHealth() + skill.getSkillValue()));
                attackEnemy(username, currentHero.getBaseAttack(), currentHero.getBaseDefense(), false, true);
            }
            case IMMOBILIZE -> attackEnemy(username, currentHero.getBaseAttack() + skill.getSkillValue(), currentHero.getBaseDefense(), true, false);
        }

        return createCombatStatusView();
    }

    private CombatStatusViewModel attackEnemy(String username, Integer heroDamage, Integer heroDefense, Boolean immobilized, Boolean healing) {
        if (!currentEnemy.getIsAlive() || !currentHero.getIsAlive()) return createCombatStatusView();

        recoverMana();
        lowerCoolDowns();

        if (currentHero.getCurrentHealth() > 0 && !healing) {
            currentEnemy.setCurrentHealth(Math.max(0, currentEnemy.getCurrentHealth() - heroDamage));
        }

        if (currentEnemy.getCurrentHealth() > 0 && !immobilized) {
            currentHero.setCurrentHealth(
                    Math.max(0, currentHero.getCurrentHealth() - Math.max(0, currentEnemy.getAttack() - heroDefense)));
        }

        if (currentEnemy.getCurrentHealth() <= 0) uponEnemyDeath(username);
        if (currentHero.getCurrentHealth() <= 0) uponHeroDeath();

        return createCombatStatusView();
    }

    private void recoverMana() {
        currentHero.setCurrentMana(Math.min(currentHero.getBaseMana(),
                (int) (currentHero.getCurrentMana() + currentHero.getBaseMana() * 0.1)));
    }

    private void lowerCoolDowns() {
        currentHero.getSkillList()
                .stream()
                .filter(skillViewModel -> skillViewModel.getCurrentCoolDown() > 0)
                .forEach(skillViewModel -> skillViewModel.setCurrentCoolDown(skillViewModel.getCurrentCoolDown() - 1));
    }

    private void uponEnemyDeath(String username) {
        resetSkillCooDowns();
        currentEnemy.setIsAlive(false);
        earnGold(username);

        heroService.gainExperience(currentHero.getId(),
                currentHero.getLevel() > currentEnemy.getLevel() ? currentEnemy.getExperience() / 2 : currentEnemy.getExperience());
        if (currentHero.getLevel() < heroService.findHeroById(currentHero.getId()).getLevel()) updateCurrentHero(username);
    }

    private void resetSkillCooDowns() {
        currentHero.getSkillList().forEach(skillViewModel -> skillViewModel.setCurrentCoolDown(0));
    }

    private void earnGold(String username) {
        Integer goldDrop = ThreadLocalRandom.current().nextInt(currentEnemy.getGoldDropLowerThreshold(), currentEnemy.getGoldDropUpperThreshold() + 1);
        UserEntity userEntity = getUserByUsername(username);
        userEntity.setGold(userEntity.getGold() + goldDrop);
        userRepository.save(userEntity);
    }

    private void uponHeroDeath() {
        resetSkillCooDowns();
        currentHero.setIsAlive(false);
    }

    private CombatStatusViewModel createCombatStatusView() {
        CombatStatusViewModel combatStatusViewModel = new CombatStatusViewModel();
        combatStatusViewModel.setHero(modelMapper.map(currentHero, HeroCombatViewModel.class));
        combatStatusViewModel.setEnemy(modelMapper.map(currentEnemy, EnemyViewModel.class));

        return combatStatusViewModel;
    }

    @Override
    @Transactional
    public void setCurrentHero(String username) {
        UUID currentHeroId = getUserByUsername(username)
                .getCurrentHeroId();

        checkIfTheCurrentEntityIsNull(currentHeroId == null, "There is no selected hero.");
        HeroEntity heroEntity = heroService.findHeroById(currentHeroId);
        currentHero = modelMapper.map(heroEntity, CurrentHero.class);
        currentHero
                .setCurrentHealth(currentHero.getBaseHealth())
                .setCurrentMana(currentHero.getBaseMana())
                .setSkillList(heroEntity
                        .getSkills()
                        .stream()
                        .sorted(Comparator.comparing(SkillEntity::getLevel))
                        .map(skillEntity -> modelMapper.map(skillEntity, SkillViewModel.class).setCurrentCoolDown(0))
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void updateCurrentHero(String username) {
        setCurrentHero(username);
    }

    @Override
    public void setCurrentEnemy(UUID id) {
        currentEnemy = modelMapper.map(enemyService.findById(id), CurrentEnemy.class);
        currentEnemy
                .setCurrentHealth(currentEnemy.getHealth());
    }

    @Override
    public CombatStatusViewModel resetCurrentEnemy() {
        currentEnemy
                .setIsAlive(true)
                .setCurrentHealth(currentEnemy.getHealth());
        currentHero
                .setIsAlive(true)
                .setCurrentHealth(currentHero.getBaseHealth());

        return createCombatStatusView();
    }

    private void checkIfTheCurrentEntityIsNull(Boolean condition, String message) {
        if (condition) throw new ObjectNotFoundException(message);
    }

    @Override
    @Transactional
    public void selectNewHero(String username, HeroSelectServiceModel selectedHeroId) {
        UserEntity userEntity = getUserByUsername(username);

        userEntity.setCurrentHeroId(selectedHeroId.getId());
        userRepository.save(userEntity);

        setCurrentHero(username);
    }

    @Override
    public boolean userHasNoSelectedHero(String username) {
        return getUserByUsername(username).getCurrentHeroId() == null;
    }

    @Override
    @Transactional
    public void deleteHero(String username, HeroSelectServiceModel heroModel) {
        UserEntity user = getUserByUsername(username);
        if (user.getCurrentHeroId() != null && user.getCurrentHeroId().equals(heroModel.getId()))
            user.setCurrentHeroId(null);

        user.getHeroes().remove(heroService.findHeroById(heroModel.getId()));

        userRepository.save(user);
        heroService.deleteById(heroModel.getId());
    }

    @Override
    @Transactional
    public HeroSelectedViewModel getCurrentHero(String username) {
        return mapCurrentHeroToViewModel(HeroSelectedViewModel.class, username);
    }

    @Override
    public void heroIsOverTheLevelRequirementForThatZone() {
        if (currentHero.getLevel() < currentEnemy.getZoneLevelRequirement())
            throw new UserHasNoPermissionToAccessException("Current hero level is too low for that instance.");
    }

    @Override
    @Transactional
    public HeroCombatViewModel getCurrentHeroForCombat(String username) {
        return mapCurrentHeroToViewModel(HeroCombatViewModel.class, username);
    }

    private <T> T mapCurrentHeroToViewModel(Class<T> viewClass, String username) {
        if (currentHero == null) setCurrentHero(username);
        return modelMapper.map(
                currentHero,
                viewClass);
    }

    @Override
    @Transactional
    public boolean ownsThisHero(String username, UUID selectedHeroId) {
        return getUserByUsername(username)
                .getHeroes()
                .stream()
                .anyMatch(hero -> hero.getId().equals(selectedHeroId));
    }

    @Override
    @Transactional
    public UserHeroSelectViewModel getUserWithOwnedHeroes(String username) {
        UserEntity user = getUserByUsername(username);
        HeroEntity heroEntity = new HeroEntity();
        try {
            heroEntity = heroService.
                    findHeroById(user.getCurrentHeroId());
        } catch (Exception ignore) {
        }

        return new UserHeroSelectViewModel()
                .setCurrentHero(modelMapper.map(heroEntity, HeroInfoViewModel.class))
                .setHeroes(user
                        .getHeroes()
                        .stream()
                        .sorted((h1, h2) -> h2.getLevel() - h1.getLevel())
                        .map(hero -> modelMapper.map(hero, HeroInfoViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with username %s does not exist.", username)));
    }

    @Override
    public void register(UserRegisterServiceModel userModel) {

        UserEntity user = new UserEntity(
                userModel.getUsername(),
                passwordEncoder.encode(userModel.getRawPassword()),
                userModel.getEmail(),
                Set.of(userRoleService.findByUserRole(UserRoleEnum.USER))
        );

        userRepository.save(user);

        login(user);
    }

    private void login(UserEntity user) {

        UserDetails userDetails = securityUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                user.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean isUsernameFree(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailFree(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public void initialize() {
        if (userRepository.count() != 0) return;

        List<UserEntity> userEntities = List.of(new UserEntity(
                "felin",
                passwordEncoder.encode(System.getenv("ADMIN_PASS")),
                System.getenv("FELIN_EMAIL"),
                Set.of(userRoleService.findByUserRole(UserRoleEnum.ADMIN),
                        userRoleService.findByUserRole(UserRoleEnum.USER))
        ), new UserEntity(
                "felixi",
                passwordEncoder.encode(System.getenv("ADMIN_PASS")),
                System.getenv("FELIXI_EMAIL"),
                Set.of(userRoleService.findByUserRole(UserRoleEnum.USER))
        ));

        userRepository.saveAll(userEntities);
    }
}
