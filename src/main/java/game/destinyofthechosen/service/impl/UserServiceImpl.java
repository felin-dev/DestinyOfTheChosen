package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.HeroEntity;
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
    public CombatStatusViewModel performAttackOnEnemy(String username) {

        if (currentHero == null) setCurrentHero(username);
        checkIfTheCurrentEntityIsNull(currentEnemy == null, "There is no selected enemy.");

        return attackEnemy(username);
    }

    private CombatStatusViewModel attackEnemy(String username) {
        if (!currentEnemy.getIsAlive() || !currentHero.getIsAlive()) return createCombatStatusView();

        if (currentHero.getCurrentHealth() > 0) {
            currentEnemy.setCurrentHealth(Math.max(0, currentEnemy.getCurrentHealth() - currentHero.getBaseAttack()));
        }
        if (currentEnemy.getCurrentHealth() > 0) {
            currentHero.setCurrentHealth(
                    Math.max(0, currentHero.getCurrentHealth() - Math.max(0, currentEnemy.getAttack() - currentHero.getBaseDefense())));
        }

        if (currentEnemy.getCurrentHealth() <= 0) uponEnemyDeath(username);
        if (currentHero.getCurrentHealth() <= 0) uponHeroDeath();

        return createCombatStatusView();
    }

    private void uponEnemyDeath(String username) {
        currentEnemy.setIsAlive(false);
        earnGold(username);

        heroService.gainExperience(currentHero.getId(), currentEnemy.getExperience());
        if (currentHero.getLevel() < heroService.getById(currentHero.getId()).getLevel()) updateCurrentHero(username);
    }

    private void earnGold(String username) {
        Integer goldDrop = ThreadLocalRandom.current().nextInt(currentEnemy.getGoldDropLowerThreshold(), currentEnemy.getGoldDropUpperThreshold() + 1);
        UserEntity userEntity = getUserByUsername(username);
        userEntity.setGold(userEntity.getGold() + goldDrop);
        userRepository.save(userEntity);
    }

    private void uponHeroDeath() {
        currentHero.setIsAlive(false);
    }

    private CombatStatusViewModel createCombatStatusView() {
        CombatStatusViewModel combatStatusViewModel = new CombatStatusViewModel();
        combatStatusViewModel.setHero(modelMapper.map(currentHero, HeroCombatViewModel.class));
        combatStatusViewModel.setEnemy(modelMapper.map(currentEnemy, EnemyViewModel.class));
        return combatStatusViewModel;
    }

    @Override
    public void setCurrentHero(String username) {
        UUID currentHeroId = getUserByUsername(username)
                .getCurrentHeroId();

        checkIfTheCurrentEntityIsNull(currentHeroId == null, "There is no selected hero.");
        currentHero = modelMapper.map(heroService.getById(currentHeroId), CurrentHero.class);
        currentHero
                .setCurrentHealth(currentHero.getBaseHealth())
                .setCurrentMana(currentHero.getBaseMana());
    }

    @Override
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
    public String resetCurrentEnemy() {
        currentEnemy
                .setIsAlive(true)
                .setCurrentHealth(currentEnemy.getHealth());

        return currentEnemy.getName();
    }

    private void checkIfTheCurrentEntityIsNull(Boolean condition, String message) {
        if (condition) throw new ObjectNotFoundException(message);
    }

    @Override
    public void selectNewHero(String username, HeroSelectServiceModel selectedHero) {
        UserEntity userEntity = getUserByUsername(username);

        userEntity.setCurrentHeroId(selectedHero.getId());
        userRepository.save(userEntity);

        setCurrentHero(username);
    }

    @Override
    public boolean userHasNoSelectedHero(String username) {
        return getUserByUsername(username).getCurrentHeroId() == null;
    }

    @Override
    public void deleteHero(String username, HeroSelectServiceModel heroModel) {
        UserEntity user = getUserByUsername(username);
        if (user.getCurrentHeroId() != null && user.getCurrentHeroId().equals(heroModel.getId()))
            user.setCurrentHeroId(null);

        user.getHeroes().remove(heroService.getById(heroModel.getId()));

        userRepository.save(user);
        heroService.deleteById(heroModel.getId());
    }

    @Override
    public HeroSelectedViewModel getCurrentHero(String username) {
        return mapCurrentHeroToViewModel(username, HeroSelectedViewModel.class);
    }

    @Override
    public HeroCombatViewModel getCurrentHeroForCombat(String username) {
        HeroCombatViewModel heroCombatViewModel = mapCurrentHeroToViewModel(username, HeroCombatViewModel.class);
        heroCombatViewModel
                .setCurrentHealth(heroCombatViewModel.getBaseHealth())
                .setCurrentMana(heroCombatViewModel.getBaseMana());

        return heroCombatViewModel;
    }

    private <T> T mapCurrentHeroToViewModel(String username, Class<T> viewClass) {
        return modelMapper.map(
                heroService.getById(getUserByUsername(username).getCurrentHeroId()),
                viewClass);
    }

    @Override
    public boolean isOverTheLevelRequirement(String username, Integer levelRequirement) {
        HeroEntity heroEntity = heroService.getById(getUserByUsername(username).getCurrentHeroId());

        return heroEntity.getLevel() >= levelRequirement;
    }

    @Override
    public boolean ownsThisHero(String username, UUID selectedHeroId) {
        return getUserByUsername(username)
                .getHeroes()
                .stream()
                .anyMatch(hero -> hero.getId().equals(selectedHeroId));
    }

    @Override
    public UserHeroSelectViewModel getUserWithOwnedHeroes(String username) {
        UserEntity user = getUserByUsername(username);
        HeroEntity heroEntity = new HeroEntity();
        try {
            heroEntity = heroService.
                    getById(user.getCurrentHeroId());
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
}
