package game.destinyofthechosen.service;

import game.destinyofthechosen.config.ApplicationBeanConfiguration;
import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.initialize.CommandRunner;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.StatUpServiceModel;
import game.destinyofthechosen.model.session.CurrentEnemy;
import game.destinyofthechosen.model.session.CurrentHero;
import game.destinyofthechosen.repository.*;
import game.destinyofthechosen.service.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({HeroServiceImpl.class, ApplicationBeanConfiguration.class, CurrentHero.class, CurrentEnemy.class, CommandRunner.class,
        UserServiceImpl.class, ZoneServiceImpl.class, EnemyServiceImpl.class, ItemServiceImpl.class, SkillServiceImpl.class,
        UserRoleServiceImpl.class, SecurityUserServiceImpl.class})
@AutoConfigureTestDatabase
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL9Dialect")
public class HeroServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private EnemyRepository enemyRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommandRunner commandRunner;
    @Autowired
    private HeroServiceImpl heroService;
    @Autowired
    private CurrentHero currentHero;
    @Autowired
    private CurrentEnemy currentEnemy;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ZoneServiceImpl zoneService;
    @Autowired
    private EnemyServiceImpl enemyService;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private SkillServiceImpl skillService;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void testIsHeroNameFreeWithFreeUsername() {
        Assertions.assertTrue(heroService.isHeroNameFree("made_up_username"));
    }

    @Test
    void testHeroDeletion() {
        Assertions.assertEquals(3, heroRepository.count());
        heroService.deleteById(heroRepository.findHeroByHeroName("Jessica").getId());
        Assertions.assertEquals(2, heroRepository.count());
    }

    @Test
    void testIsHeroNameFreeWithTakenUsername() {
        Assertions.assertFalse(heroService.isHeroNameFree("Felixi"));
    }

    @Test
    void testIsOverTheLevelRequirementThrowsUserNotFoundException() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> heroService.isOverTheLevelRequirement("made_up_username", 5),
                "User with username made_up_username does not exist.");
    }

    @Test
    void testIsOverTheLevelRequirementThrowsHeroNotFoundException() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> heroService.isOverTheLevelRequirement("felin", 5),
                "There is no hero with such id.");
    }

    @Test
    void testCreateNewHero() {
        UserEntity user = heroService.getUserByUsername("felin");
        Assertions.assertNull(user.getCurrentHeroId());

        heroService.createNewHero(
                new HeroCreationServiceModel().setHeroName("hunter").setHeroRole(HeroRoleEnum.HUNTER), "felin");

        HeroEntity hunter = heroRepository.findHeroByHeroName("hunter");

        Assertions.assertEquals("hunter", hunter.getHeroName());
        Assertions.assertEquals(HeroRoleEnum.HUNTER, hunter.getHeroRole());
        Assertions.assertEquals(29, hunter.getBaseAttack());
        Assertions.assertEquals(4, hunter.getBaseMagicPower());
        Assertions.assertEquals(120, hunter.getBaseHealth());
        Assertions.assertEquals(20, hunter.getBaseMana());
        Assertions.assertEquals(10, hunter.getBaseDefense());
        Assertions.assertEquals(12, hunter.getBaseDexterity());
        Assertions.assertEquals(5, hunter.getBaseStrength());
        Assertions.assertEquals(4, hunter.getBaseEnergy());
        Assertions.assertEquals(6, hunter.getBaseVitality());

        heroService.createNewHero(
                new HeroCreationServiceModel().setHeroName("warrior").setHeroRole(HeroRoleEnum.WARRIOR), "felin");

        HeroEntity warrior = heroRepository.findHeroByHeroName("warrior");

        Assertions.assertEquals("warrior", warrior.getHeroName());
        Assertions.assertEquals(HeroRoleEnum.WARRIOR, warrior.getHeroRole());
        Assertions.assertEquals(17, warrior.getBaseAttack());
        Assertions.assertEquals(5, warrior.getBaseMagicPower());
        Assertions.assertEquals(180, warrior.getBaseHealth());
        Assertions.assertEquals(25, warrior.getBaseMana());
        Assertions.assertEquals(12, warrior.getBaseDefense());
        Assertions.assertEquals(5, warrior.getBaseDexterity());
        Assertions.assertEquals(6, warrior.getBaseStrength());
        Assertions.assertEquals(5, warrior.getBaseEnergy());
        Assertions.assertEquals(9, warrior.getBaseVitality());

        heroService.createNewHero(
                new HeroCreationServiceModel().setHeroName("mage").setHeroRole(HeroRoleEnum.MAGE), "felin");

        HeroEntity mage = heroRepository.findHeroByHeroName("mage");

        Assertions.assertEquals("mage", mage.getHeroName());
        Assertions.assertEquals(HeroRoleEnum.MAGE, mage.getHeroRole());
        Assertions.assertEquals(8, mage.getBaseAttack());
        Assertions.assertEquals(28, mage.getBaseMagicPower());
        Assertions.assertEquals(140, mage.getBaseHealth());
        Assertions.assertEquals(70, mage.getBaseMana());
        Assertions.assertEquals(8, mage.getBaseDefense());
        Assertions.assertEquals(4, mage.getBaseDexterity());
        Assertions.assertEquals(4, mage.getBaseStrength());
        Assertions.assertEquals(14, mage.getBaseEnergy());
        Assertions.assertEquals(7, mage.getBaseVitality());

        Assertions.assertNotNull(user.getCurrentHeroId());
        Assertions.assertEquals("mage", heroRepository.findHeroById(user.getCurrentHeroId()).get().getHeroName());
    }

    @Test
    void testIsOverTheLevelRequirementTrue() {
        userService.getUserByUsername("felin").setCurrentHeroId(heroRepository.findHeroByHeroName("Felixi").getId());
        Assertions.assertTrue(heroService.isOverTheLevelRequirement("felin", 1));
    }

    @Test
    void testIsOverTheLevelRequirementFalse() {
        userService.getUserByUsername("felin").setCurrentHeroId(heroRepository.findHeroByHeroName("Felixi").getId());
        Assertions.assertFalse(heroService.isOverTheLevelRequirement("felin", 5));
    }

    @Test
    void testCurrentHeroIsOverTheLevelRequirementThrowsException() {
        heroService.setCurrentHeroForTesting(new CurrentHero());
        heroService.setCurrentEnemyForTesting(new CurrentEnemy());
        userService.selectNewHero("felin",
                new HeroSelectServiceModel()
                        .setId(heroRepository.findHeroByHeroName("Felixi").getId()));
        heroService.setCurrentEnemy(enemyRepository.findByEnemyName("Trennaxath").getId());
        Assertions.assertThrows(UserHasNoPermissionToAccessException.class,
                () -> heroService.currentHeroIsOverTheLevelRequirementForThatZone(),
                "Current hero level is too low for that instance.");
    }

    @Test
    void testCurrentHeroIsOverTheLevelRequirementWorksProperly() {
        heroService.setCurrentHeroForTesting(new CurrentHero());
        heroService.setCurrentEnemyForTesting(new CurrentEnemy());
        userService.selectNewHero("felin",
                new HeroSelectServiceModel()
                        .setId(heroRepository.findHeroByHeroName("Felixi").getId()));
        heroService.setCurrentEnemy(enemyRepository.findByEnemyName("Baby Boar").getId());
        Assertions.assertDoesNotThrow(heroService::currentHeroIsOverTheLevelRequirementForThatZone);
    }

    @Test
    void testEquipWeaponThrowsExceptionForItemNotOwned() {
        heroService.setCurrentHeroForTesting(new CurrentHero());
        heroService.setCurrentEnemyForTesting(new CurrentEnemy());
        userService.selectNewHero("felin",
                new HeroSelectServiceModel()
                        .setId(heroRepository.findHeroByHeroName("Felixi").getId()));

        ItemEntity item = itemRepository.findByItemName("Broad Sword");
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> heroService.equipWeapon(item.getId(), "Felixi"),
                "Hero does not own item with id: " + item.getId());
    }

    @Test
    void testEquipWeaponWarrior() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Jessica");

        Assertions.assertEquals(17, hero.getBaseAttack());
        ItemEntity item = itemRepository.findByItemName("Broad Sword");
        item.setLevelRequirement(1);
        hero.addItem(item);
        heroRepository.save(hero);
        heroService.setCurrentHero("felin");

        Assertions.assertDoesNotThrow(() -> heroService.equipWeapon(item.getId(), "felin"));
        Assertions.assertEquals(item.getId(), hero.getEquippedWeapon());
        Assertions.assertEquals(hero.getBaseAttack() + 23, currentHero.getBaseAttack());
        Assertions.assertEquals(hero.getBaseHealth() + 140, currentHero.getBaseHealth());
    }

    @Test
    void testEquipWeaponHunter() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Felixi");

        Assertions.assertEquals(29, hero.getBaseAttack());
        ItemEntity item = itemRepository.findByItemName("Composite Bow");
        item.setLevelRequirement(1);
        hero.addItem(item);
        heroRepository.save(hero);
        heroService.setCurrentHero("felin");

        Assertions.assertDoesNotThrow(() -> heroService.equipWeapon(item.getId(), "felin"));
        Assertions.assertEquals(item.getId(), hero.getEquippedWeapon());
        Assertions.assertEquals(hero.getBaseAttack() + 48, currentHero.getBaseAttack());
        Assertions.assertEquals(hero.getBaseHealth() + 120, currentHero.getBaseHealth());
    }

    @Test
    void testEquipWeaponMage() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "SpiritOfTheElder");

        Assertions.assertEquals(8, hero.getBaseAttack());
        ItemEntity item = itemRepository.findByItemName("Ash Wood Scepter");
        item.setLevelRequirement(1);
        hero.addItem(item);
        heroRepository.save(hero);
        heroService.setCurrentHero("felin");

        Assertions.assertDoesNotThrow(() -> heroService.equipWeapon(item.getId(), "felin"));
        Assertions.assertEquals(item.getId(), hero.getEquippedWeapon());
        Assertions.assertEquals(hero.getBaseAttack(), currentHero.getBaseAttack());
        Assertions.assertEquals(hero.getBaseMagicPower() + 50, currentHero.getBaseMagicPower());
        Assertions.assertEquals(hero.getBaseMana() + 40, currentHero.getBaseMana());
        Assertions.assertEquals(hero.getBaseHealth() + 100, currentHero.getBaseHealth());
    }

    @Test
    void testUnequipWeapon() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Jessica");

        Assertions.assertEquals(17, hero.getBaseAttack());
        ItemEntity item = itemRepository.findByItemName("Broad Sword");
        item.setLevelRequirement(1);
        hero.addItem(item);
        heroRepository.save(hero);
        heroService.setCurrentHero("felin");

        heroService.equipWeapon(item.getId(), "felin");
        heroService.unequipWeapon(item.getId(), "felin");
        Assertions.assertEquals(hero.getBaseAttack(), currentHero.getBaseAttack());
        Assertions.assertNull(currentHero.getEquippedWeapon().getId());
    }

    @Test
    void testThrowItem() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Jessica");

        Assertions.assertEquals(17, hero.getBaseAttack());
        ItemEntity item = itemRepository.findByItemName("Broad Sword");
        item.setLevelRequirement(1);
        hero.addItem(item);
        heroRepository.save(hero);
        heroService.setCurrentHero("felin");

        Assertions.assertEquals(1, currentHero.getItems().size());
        heroService.throwItem(item.getId(), "felin");
        Assertions.assertEquals(0, currentHero.getItems().size());
    }

    @Test
    void testAddStatsThrowsExceptionForNotEnoughStatPoints() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Jessica");


        Assertions.assertThrows(UserHasNoPermissionToAccessException.class,
                () -> heroService.addStats(new StatUpServiceModel().setStrength(1), "felin"),
                "Hero does not have enough stats.");
    }

    @Test
    void testAddStatsCorrectly() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Jessica");

        hero.levelUp();
        heroRepository.save(hero);

        int strengthBeforeAdding = hero.getBaseStrength();
        int vitalityBeforeAdding = hero.getBaseVitality();
        int dexterityBeforeAdding = hero.getBaseDexterity();
        Assertions.assertDoesNotThrow(() ->
                heroService.addStats(new StatUpServiceModel()
                        .setStrength(4)
                        .setVitality(3)
                        .setDexterity(1), "felin"));

        Assertions.assertEquals(strengthBeforeAdding + 4, currentHero.getBaseStrength());
        Assertions.assertEquals(vitalityBeforeAdding + 3, currentHero.getBaseVitality());
        Assertions.assertEquals(dexterityBeforeAdding + 1, currentHero.getBaseDexterity());
        Assertions.assertEquals(strengthBeforeAdding + 4, hero.getBaseStrength());
        Assertions.assertEquals(vitalityBeforeAdding + 3, hero.getBaseVitality());
        Assertions.assertEquals(dexterityBeforeAdding + 1, hero.getBaseDexterity());
    }

    @Test
    void testUpdateCurrentHero() {
        CurrentHero currentHero = new CurrentHero();
        CurrentEnemy currentEnemy = new CurrentEnemy();
        HeroEntity hero = selectHeroJessicaOnUserFelin(currentHero, currentEnemy, "felin", "Jessica");

        Assertions.assertEquals(1, hero.getLevel());
        Assertions.assertEquals(1, currentHero.getLevel());

        hero.levelUp();
        heroRepository.save(hero);

        Assertions.assertEquals(2, hero.getLevel());
        Assertions.assertEquals(1, currentHero.getLevel());

        heroService.updateCurrentHero("felin");
        Assertions.assertEquals(2, currentHero.getLevel());
    }

    private HeroEntity selectHeroJessicaOnUserFelin(CurrentHero currentHero, CurrentEnemy currentEnemy, String username, String heroName) {
        heroService.setCurrentHeroForTesting(currentHero);
        heroService.setCurrentEnemyForTesting(currentEnemy);

        HeroEntity hero = heroRepository.findHeroByHeroName(heroName);
        userService.selectNewHero(username,
                new HeroSelectServiceModel()
                        .setId(hero.getId()));
        return hero;
    }
}
