package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.SkillEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.model.session.CurrentEnemy;
import game.destinyofthechosen.model.session.CurrentHero;
import game.destinyofthechosen.model.view.*;
import game.destinyofthechosen.repository.HeroRepository;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.ItemService;
import game.destinyofthechosen.service.SkillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private static final String WARRIOR_IMAGE = "https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/warrior-f.png";
    private static final String HUNTER_IMAGE = "https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/hunter-f.png";
    private static final String MAGE_IMAGE = "https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/mage-f.png";
    private static final Map<Integer, Integer> LEVELING = new LinkedHashMap<>();

    private final HeroRepository heroRepository;
    private final UserRepository userRepository;
    private final EnemyService enemyService;
    private final ItemService itemService;
    private final SkillService skillService;
    private final ModelMapper modelMapper;

    private CurrentHero currentHero;
    private CurrentEnemy currentEnemy;
    private String itemDrop = null;
    private Integer moneyDrop = null;
    private Integer leveledUp = null;

    public HeroServiceImpl(HeroRepository heroRepository, UserRepository userRepository, EnemyService enemyService, ItemService itemService, SkillService skillService, ModelMapper modelMapper) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
        this.enemyService = enemyService;
        this.itemService = itemService;
        this.skillService = skillService;
        this.modelMapper = modelMapper;
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

    @Override
    @Transactional
    public CombatStatusViewModel performAttackOnEnemy(String username) {

        if (currentHero == null) setCurrentHero(username);
        checkIfTheCurrentEntityIsNull(currentEnemy == null, "There is no selected enemy.");

        return attackEnemy(username, currentHero.getBaseAttack(), currentHero.getBaseDefense(), false, false);
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

    private void uponHeroDeath() {
        resetSkillCooDowns();
        currentHero.setIsAlive(false);
    }

    private void uponEnemyDeath(String username) {
        resetSkillCooDowns();
        currentEnemy.setIsAlive(false);
        earnGold(username);

        if (currentEnemy.getDropList().size() > 0 && itemDropped()) {
            addItemToHero();
        }

        gainExperience(currentHero.getId(),
                currentHero.getLevel() > currentEnemy.getLevel() ? currentEnemy.getExperience() / 2 : currentEnemy.getExperience());

        if (currentHero.getLevel() < findHeroById(currentHero.getId()).getLevel()) updateCurrentHero(username);
    }

    public void gainExperience(UUID id, Integer experience) {
        HeroEntity heroEntity = findHeroById(id);
        heroEntity.setExperience(heroEntity.getExperience() + experience);
        checkIfHeroHasToLevelUp(heroEntity);

        heroRepository.save(heroEntity);
    }

    private void checkIfHeroHasToLevelUp(HeroEntity heroEntity) {
        Integer currentLevel = heroEntity.getLevel();
        if (currentLevel == 20) return;

        Integer experienceNeededForLevelingUp = LEVELING.get(currentLevel + 1);
        if (heroEntity.getExperience() >= experienceNeededForLevelingUp) {
            heroEntity.levelUp();
            leveledUp = heroEntity.getLevel();
        }
    }

    private void earnGold(String username) {
        Integer goldDrop = getRandomNumberInRange(currentEnemy.getGoldDropLowerThreshold(), currentEnemy.getGoldDropUpperThreshold() + 1);

        this.moneyDrop = goldDrop;
        UserEntity userEntity = getUserByUsername(username);
        userEntity.setGold(userEntity.getGold() + goldDrop);
        userRepository.save(userEntity);
    }

    private void addItemToHero() {
        Integer itemIndex = getRandomNumberInRange(0, currentEnemy.getDropList().size());
        ItemEntity itemEntity = itemService.getItemById(currentEnemy.getDropList().get(itemIndex).getId());

        HeroEntity heroEntity = heroRepository.getById(currentHero.getId());
        heroEntity.addItem(itemEntity);
        itemDrop = itemEntity.getItemName();
        heroRepository.save(heroEntity);
    }

    private boolean itemDropped() {
        return getRandomNumberInRange(0, 100 + 1) < 30;
    }

    private Integer getRandomNumberInRange(Integer lowerBound, Integer upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
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

    private void resetSkillCooDowns() {
        currentHero.getSkillList().forEach(skillViewModel -> skillViewModel.setCurrentCoolDown(0));
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

    private CombatStatusViewModel createCombatStatusView() {
        CombatStatusViewModel combatStatusViewModel = new CombatStatusViewModel();
        combatStatusViewModel.setHero(modelMapper.map(currentHero, HeroCombatViewModel.class));
        combatStatusViewModel.setEnemy(modelMapper.map(currentEnemy, EnemyViewModel.class));

        combatStatusViewModel.setMoneyDrop(moneyDrop);
        combatStatusViewModel.setLeveledUp(leveledUp);
        combatStatusViewModel.setItemDrop(itemDrop);
        cleanInfo();

        return combatStatusViewModel;
    }

    private void cleanInfo() {
        moneyDrop = null;
        leveledUp = null;
        itemDrop = null;
    }

    @Override
    public void createNewHero(HeroCreationServiceModel heroModel, String username) {

        UserEntity userEntity = getUserByUsername(username);

        HeroEntity newHeroEntity = createNewHeroEntity(heroModel);
        newHeroEntity.setUser(userEntity);
        heroRepository.save(newHeroEntity);

        userEntity.setCurrentHeroId(newHeroEntity.getId());
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void setCurrentHero(String username) {
        UUID currentHeroId = getUserByUsername(username)
                .getCurrentHeroId();

        checkIfTheCurrentEntityIsNull(currentHeroId == null, "There is no selected hero.");
        HeroEntity heroEntity = findHeroById(currentHeroId);
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

    private void checkIfTheCurrentEntityIsNull(Boolean condition, String message) {
        if (condition) throw new ObjectNotFoundException(message);
    }

    @Override
    @Transactional
    public HeroSelectedViewModel getCurrentHero(String username) {
        return mapCurrentHeroToViewModel(HeroSelectedViewModel.class, username);
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
    public void heroIsOverTheLevelRequirementForThatZone() {
        if (currentHero.getLevel() < currentEnemy.getZoneLevelRequirement())
            throw new UserHasNoPermissionToAccessException("Current hero level is too low for that instance.");
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with username %s does not exist.", username)));
    }

    private HeroEntity createNewHeroEntity(HeroCreationServiceModel heroModel) {

        HeroEntity heroEntity = new HeroEntity(heroModel.getHeroName(), heroModel.getHeroRole());

        switch (heroEntity.getHeroRole()) {
            case WARRIOR -> createWarrior(heroEntity);
            case HUNTER -> createHunter(heroEntity);
            case MAGE -> createMage(heroEntity);
        }

        return heroEntity;
    }

    private void createWarrior(HeroEntity warrior) {
        warrior
                .setBaseDefense(12)     // base defense increases with items and levels
                .setBaseDexterity(5)    // attack = dexterity x1
                .setBaseStrength(6)     // attack = strength x2
                .setBaseEnergy(5)       // mana = energy x20, magic power = energy x1
                .setBaseVitality(9)     // health = vitality x20
                .setImageUrl(WARRIOR_IMAGE)
                .setSkills(List.of(skillService.findByNameAndLevel("Shield Bash", 1)));
    }

    private void createHunter(HeroEntity hunter) {
        hunter
                .setBaseDefense(10)     // base defense increases with items and levels
                .setBaseDexterity(10)   // attack = dexterity x2
                .setBaseStrength(5)     // attack = strength x1
                .setBaseEnergy(4)       // mana = energy x20, magic power = energy x1
                .setBaseVitality(6)     // health = vitality x20
                .setImageUrl(HUNTER_IMAGE)
                .setSkills(List.of(skillService.findByNameAndLevel("Double Arrow", 1)));
    }

    private void createMage(HeroEntity mage) {
        mage
                .setBaseDefense(8)      // base defense increases with items and levels
                .setBaseDexterity(4)    // attack = dexterity x1
                .setBaseStrength(4)     // attack = strength x1
                .setBaseEnergy(12)      // mana = energy x20, magic power = energy x2
                .setBaseVitality(5)     // health = vitality x20
                .setImageUrl(MAGE_IMAGE)
                .setSkills(List.of(skillService.findByNameAndLevel("Fireball", 1)));
    }

    @Override
    public HeroEntity findHeroById(UUID id) {
        return heroRepository.findHeroById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no hero with such id."));
    }

    @Override
    public void deleteById(UUID id) {
        heroRepository.deleteById(id);
    }

    @Override
    public boolean isOverTheLevelRequirement(String username, Integer levelRequirement) {
        HeroEntity heroEntity = findHeroById(getUserByUsername(username).getCurrentHeroId());

        return heroEntity.getLevel() >= levelRequirement;
    }

    @Override
    public boolean isHeroNameFree(String heroName) {
        return !heroRepository.existsByHeroName(heroName);
    }

    @Override
    public void initialize() {
        if (heroRepository.count() != 0) return;
        HeroEntity hunter = new HeroEntity("Felixi", HeroRoleEnum.HUNTER).setUser(getUserByUsername("felin"));
        createHunter(hunter);
        HeroEntity warrior = new HeroEntity("Jessica", HeroRoleEnum.WARRIOR).setUser(getUserByUsername("felin"));
        createWarrior(warrior);
        HeroEntity mage = new HeroEntity("SpiritOfTheElder", HeroRoleEnum.MAGE).setUser(getUserByUsername("felin"));
        createMage(mage);

        List<HeroEntity> heroEntities = List.of(hunter, warrior, mage);

        heroRepository.saveAll(heroEntities);
    }

    static {
        Integer experience = 1200;

        for (int i = 2; i <= 20; i++) {
            LEVELING.put(i, experience);
            experience += experience;
        }
    }
}
