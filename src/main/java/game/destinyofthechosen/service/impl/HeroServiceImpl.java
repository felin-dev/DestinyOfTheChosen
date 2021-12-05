package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.SkillEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.model.service.StatUpServiceModel;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(HeroServiceImpl.class);
    private static final Boolean TEST_MODE = false;

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

        CombatStatusViewModel combatStatusViewModel = new CombatStatusViewModel();

        int damageSkillDamage = currentHero.getBaseAttack() + currentHero.getBaseMagicPower() + (skill.getSkillValue() * currentHero.getLevel());
        int healAmount = Math.min(currentHero.getBaseHealth(), currentHero.getCurrentHealth() + (skill.getSkillValue() * currentHero.getLevel() / 3));
        int immobilizeSkillDamage = currentHero.getBaseAttack() + (skill.getSkillValue() * currentHero.getLevel());

        switch (skill.getType()) {
            case DAMAGE -> combatStatusViewModel = attackEnemy(username, damageSkillDamage, currentHero.getBaseDefense(), false, false);
            case DEFENSE_BUFF -> combatStatusViewModel = attackEnemy(username, currentHero.getBaseAttack(), currentHero.getBaseDefense() + skill.getSkillValue(), false, false);
            case HEAL -> {
                currentHero.setCurrentHealth(healAmount);
                combatStatusViewModel = attackEnemy(username, currentHero.getBaseAttack(), currentHero.getBaseDefense(), false, true);
            }
            case IMMOBILIZE -> combatStatusViewModel = attackEnemy(username, immobilizeSkillDamage, currentHero.getBaseDefense(), true, false);
        }

        return combatStatusViewModel;
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

        if (currentEnemy.getDropList().size() > 0 && itemDropped() || currentEnemy.getDropList().size() > 0 && TEST_MODE) {
            addItemToHero();
        }

        gainExperience(currentHero.getId(),
                currentHero.getLevel() > currentEnemy.getLevel() ? currentEnemy.getExperience() / 4 : currentEnemy.getExperience());

        if (currentHero.getLevel() < getHeroById(currentHero.getId()).getLevel()) updateCurrentHero(username);
    }

    public void gainExperience(UUID id, Integer experience) {
        HeroEntity heroEntity = getHeroById(id);
        int experienceGain = heroEntity.getExperience() + experience;

        if (TEST_MODE) experienceGain = currentHero.getLevel() > currentEnemy.getLevel() ? 0 : experienceGain * 5;

        heroEntity.setExperience(experienceGain);
        checkIfHeroHasToLevelUp(heroEntity);

        heroRepository.save(heroEntity);
    }

    private void checkIfHeroHasToLevelUp(HeroEntity heroEntity) {
        Integer currentLevel = heroEntity.getLevel();
        if (currentLevel == 20) return;

        Integer experienceNeededForLevelingUp = LEVELING.get(currentLevel + 1);
        if (heroEntity.getExperience() >= experienceNeededForLevelingUp) {
            levelUp(heroEntity);
        }
    }

    private void levelUp(HeroEntity heroEntity) {
        heroEntity.levelUp();
        leveledUp = heroEntity.getLevel();
        if (leveledUp == 5 || leveledUp == 10 || leveledUp == 14 || leveledUp == 17) {
            addNewSkill(heroEntity);
        }

        heroRepository.save(heroEntity);
    }

    private void addNewSkill(HeroEntity heroEntity) {
        switch (heroEntity.getLevel()) {
            case 5 -> heroEntity.addSkill(skillService.findByRoleAndLevel(heroEntity.getHeroRole(), 5));
            case 10 -> heroEntity
                    .removeSkill(skillService.findByRoleAndLevel(heroEntity.getHeroRole(), 1))
                    .addSkill(skillService.findByRoleAndLevel(heroEntity.getHeroRole(), 10));
            case 14 -> heroEntity
                    .addSkill(skillService.findByRoleAndLevel(heroEntity.getHeroRole(), 14));
            case 17 -> heroEntity
                    .removeSkill(skillService.findByRoleAndLevel(heroEntity.getHeroRole(), 5))
                    .addSkill(skillService.findByRoleAndLevel(heroEntity.getHeroRole(), 17));
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
        int itemIndex = getRandomNumberInRange(0, currentEnemy.getDropList().size());
        UUID enemyDropId = currentEnemy.getDropList().get(itemIndex).getId();

        if (currentHero.getItems().stream().anyMatch(itemViewModel -> itemViewModel.getId().equals(enemyDropId)) ||
                currentHero.getEquippedWeapon().getId() != null && currentHero.getEquippedWeapon().getId().equals(enemyDropId))
            return;

        logger.info("Hero {} received item with id: {}.", currentHero.getName(), enemyDropId);
        ItemEntity itemEntity = itemService.getItemById(enemyDropId);

        HeroEntity heroEntity = heroRepository.getById(currentHero.getId());
        heroEntity.addItem(itemEntity);
        itemDrop = itemEntity.getItemName();
        heroRepository.save(heroEntity);
    }

    private boolean itemDropped() {
        int dropChance = 30 - currentEnemy.getLevel();
        return getRandomNumberInRange(0, 100 + 1) < dropChance;
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
    public CurrentHeroViewModel getCurrentHeroInfo(String username) {
        return mapCurrentHeroToViewModel(CurrentHeroViewModel.class, username);
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
    public void addStats(StatUpServiceModel stats, String username) {
        setCurrentHero(username);
        HeroEntity heroEntity = heroRepository.getById(currentHero.getId());
        heroEntity.setStatPoints(heroEntity.getStatPoints() - totalStats(stats));
        heroEntity
                .setBaseStrength(heroEntity.getBaseStrength() +
                        Optional.ofNullable(stats.getStrength()).orElse(0))
                .setBaseDexterity(heroEntity.getBaseDexterity() +
                        Optional.ofNullable(stats.getDexterity()).orElse(0))
                .setBaseEnergy(heroEntity.getBaseEnergy() +
                        Optional.ofNullable(stats.getEnergy()).orElse(0))
                .setBaseVitality(heroEntity.getBaseVitality() +
                        Optional.ofNullable(stats.getVitality()).orElse(0));

        heroRepository.save(heroEntity);
        setCurrentHero(username);
    }

    private Integer totalStats(StatUpServiceModel stats) {
        return Optional.ofNullable(stats.getStrength()).orElse(0) + Optional.ofNullable(stats.getDexterity()).orElse(0)
                + Optional.ofNullable(stats.getEnergy()).orElse(0) + Optional.ofNullable(stats.getVitality()).orElse(0);
    }

    @Override
    @Transactional
    public void equipWeapon(UUID weaponId, String username) {
        setCurrentHero(username);
        ItemViewModel itemView = currentHero
                .getItems()
                .stream()
                .filter(itemViewModel -> itemViewModel.getId().equals(weaponId))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Hero does not own item with id: " + weaponId));

        if (itemView.getLevelRequirement() > currentHero.getLevel()) return;

        HeroEntity heroEntity = getHeroById(currentHero.getId());
        heroEntity.setEquippedWeapon(weaponId);
        heroRepository.save(heroEntity);
        setCurrentHero(username);
    }

    @Override
    @Transactional
    public void unequipWeapon(UUID id, String username) {
        setCurrentHero(username);
        currentHero.setEquippedWeapon(null);
        HeroEntity heroEntity = getHeroById(currentHero.getId());
        heroEntity.setEquippedWeapon(null);
        heroRepository.save(heroEntity);
        setCurrentHero(username);
    }

    @Override
    @Transactional
    public void throwItem(UUID id, String username) {
        setCurrentHero(username);
        HeroEntity heroEntity = getHeroById(currentHero.getId());
        heroEntity.throwItem(itemService.getItemById(id));
        heroRepository.save(heroEntity);
        setCurrentHero(username);
    }

    @Override
    @Transactional
    public void setCurrentHero(String username) {
        UUID currentHeroId = getUserByUsername(username)
                .getCurrentHeroId();

        checkIfTheCurrentEntityIsNull(currentHeroId == null, "There is no selected hero.");
        HeroEntity heroEntity = getHeroById(currentHeroId);
        ItemViewModel itemViewModel = heroEntity.getEquippedWeapon() == null ?
                new ItemViewModel() : itemService.getItemViewById(heroEntity.getEquippedWeapon());

        currentHero = modelMapper.map(heroEntity, CurrentHero.class);
        currentHero
                .setSkillList(heroEntity
                        .getSkills()
                        .stream()
                        .sorted(Comparator.comparing(SkillEntity::getLevel))
                        .map(skillEntity -> modelMapper.map(skillEntity, SkillViewModel.class).setCurrentCoolDown(0))
                        .collect(Collectors.toList()))
                .setEquippedWeapon(itemViewModel)
                .setItems(heroEntity
                        .getItems()
                        .stream()
                        .filter(itemEntity -> !itemEntity.getId().equals(currentHero.getEquippedWeapon().getId()))
                        .map(itemEntity -> itemService.getItemViewById(itemEntity.getId()))
                        .collect(Collectors.toList()));

        addEquippedItemStats(currentHero.getEquippedWeapon());
        currentHero
                .setCurrentHealth(currentHero.getBaseHealth())
                .setCurrentMana(currentHero.getBaseMana());
    }

    private void addEquippedItemStats(ItemViewModel equippedWeapon) {

        equippedWeapon
                .getStats()
                .forEach((key, value) -> {
                    switch (currentHero.getHeroRole()) {
                        case WARRIOR -> {
                            switch (key.toUpperCase()) {
                                case "ATTACK" -> currentHero.addAttack(value);
                                case "MAGIC POWER" -> currentHero.addMagicPower(value);
                                case "DEFENSE" -> currentHero.addDefense(value);
                                case "STRENGTH" -> {
                                    currentHero.addStrength(value);
                                    currentHero.addAttack(value * 2);
                                }
                                case "DEXTERITY" -> {
                                    currentHero.addDexterity(value);
                                    currentHero.addAttack(value);
                                }
                                case "ENERGY" -> {
                                    currentHero.addEnergy(value);
                                    currentHero.addMagicPower(value);
                                    currentHero.addMana(value * 5);
                                }
                                case "VITALITY" -> {
                                    currentHero.addVitality(value);
                                    currentHero.addHealth(value * 20);
                                }
                            }
                        }
                        case HUNTER -> {
                            switch (key.toUpperCase()) {
                                case "ATTACK" -> currentHero.addAttack(value);
                                case "MAGIC POWER" -> currentHero.addMagicPower(value);
                                case "DEFENSE" -> currentHero.addDefense(value);
                                case "STRENGTH" -> {
                                    currentHero.addStrength(value);
                                    currentHero.addAttack(value);
                                }
                                case "DEXTERITY" -> {
                                    currentHero.addDexterity(value);
                                    currentHero.addAttack(value * 2);
                                }
                                case "ENERGY" -> {
                                    currentHero.addEnergy(value);
                                    currentHero.addMagicPower(value);
                                    currentHero.addMana(value * 5);
                                }
                                case "VITALITY" -> {
                                    currentHero.addVitality(value);
                                    currentHero.addHealth(value * 20);
                                }
                            }
                        }
                        case MAGE -> {
                            switch (key.toUpperCase()) {
                                case "ATTACK" -> currentHero.addAttack(value);
                                case "MAGIC POWER" -> currentHero.addMagicPower(value);
                                case "DEFENSE" -> currentHero.addDefense(value);
                                case "STRENGTH" -> {
                                    currentHero.addStrength(value);
                                    currentHero.addAttack(value);
                                }
                                case "DEXTERITY" -> {
                                    currentHero.addDexterity(value);
                                    currentHero.addAttack(value);
                                }
                                case "ENERGY" -> {
                                    currentHero.addEnergy(value);
                                    currentHero.addMagicPower(value * 2);
                                    currentHero.addMana(value * 5);
                                }
                                case "VITALITY" -> {
                                    currentHero.addVitality(value);
                                    currentHero.addHealth(value * 20);
                                }
                            }
                        }
                    }
                });
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
                .setBaseEnergy(5)       // mana = energy x5, magic power = energy x1
                .setBaseVitality(9)     // health = vitality x20
                .setImageUrl(WARRIOR_IMAGE)
                .addSkill(skillService.findByNameAndLevel("Shield Bash", 1));
    }

    private void createHunter(HeroEntity hunter) {
        hunter
                .setBaseDefense(10)     // base defense increases with items and levels
                .setBaseDexterity(12)   // attack = dexterity x2
                .setBaseStrength(5)     // attack = strength x1
                .setBaseEnergy(4)       // mana = energy x5, magic power = energy x1
                .setBaseVitality(6)     // health = vitality x20
                .setImageUrl(HUNTER_IMAGE)
                .addSkill(skillService.findByNameAndLevel("Double Arrow", 1));
    }

    private void createMage(HeroEntity mage) {
        mage
                .setBaseDefense(8)      // base defense increases with items and levels
                .setBaseDexterity(4)    // attack = dexterity x1
                .setBaseStrength(4)     // attack = strength x1
                .setBaseEnergy(14)      // mana = energy x5, magic power = energy x2
                .setBaseVitality(7)     // health = vitality x20
                .setImageUrl(MAGE_IMAGE)
                .addSkill(skillService.findByNameAndLevel("Fireball", 1));
    }

    @Override
    public HeroEntity getHeroById(UUID id) {
        return heroRepository.findHeroById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no hero with such id."));
    }

    @Override
    public void deleteById(UUID id) {
        heroRepository.deleteById(id);
    }

    @Override
    public boolean isOverTheLevelRequirement(String username, Integer levelRequirement) {
        HeroEntity heroEntity = getHeroById(getUserByUsername(username).getCurrentHeroId());

        return heroEntity.getLevel() >= levelRequirement;
    }

    @Override
    public boolean isHeroNameFree(String heroName) {
        return !heroRepository.existsByHeroName(heroName);
    }

    @Override
    public boolean hasEnoughStatPoints(int stats) {
        return currentHero.getStatPoints() >= stats;
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

        heroRepository.saveAll(List.of(hunter, warrior, mage));
    }

    static {
        Integer experience = 1200;

        for (int i = 2; i <= 20; i++) {
            LEVELING.put(i, experience);
            experience += experience;
        }

        for (Map.Entry<Integer, Integer> level : LEVELING.entrySet()) {
            logger.info("{} - {}", level.getKey(), level.getValue());
        }
    }
}
