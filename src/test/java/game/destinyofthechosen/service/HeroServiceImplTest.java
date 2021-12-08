package game.destinyofthechosen.service;

import game.destinyofthechosen.config.ApplicationBeanConfiguration;
import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.initialize.CommandRunner;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.model.session.CurrentEnemy;
import game.destinyofthechosen.model.session.CurrentHero;
import game.destinyofthechosen.repository.HeroRepository;
import game.destinyofthechosen.repository.SkillRepository;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Import({HeroServiceImpl.class, ApplicationBeanConfiguration.class, CurrentHero.class, CurrentEnemy.class, CommandRunner.class,
        UserServiceImpl.class, ZoneServiceImpl.class, EnemyServiceImpl.class, ItemServiceImpl.class, SkillServiceImpl.class,
        UserRoleServiceImpl.class, SecurityUserServiceImpl.class})
@AutoConfigureTestDatabase
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL9Dialect")
public class HeroServiceImplTest {

    @Autowired
    private CommandRunner commandRunner;
    @Autowired
    private HeroServiceImpl serviceToTest;
    @Autowired
    private CurrentHero currentHero;
    @Autowired
    private CurrentEnemy currentEnemy;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private SkillRepository skillRepository;
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
        Assertions.assertTrue(serviceToTest.isHeroNameFree("made_up_username"));
    }

    @Test
    void testIsHeroNameFreeWithTakenUsername() {
        Assertions.assertFalse(serviceToTest.isHeroNameFree("Felixi"));
    }

    @Test
    void testIsOverTheLevelRequirementTrue() {
        userService.getUserByUsername("felin").setCurrentHeroId(heroRepository.findHeroByHeroName("Felixi").getId());
        Assertions.assertTrue(serviceToTest.isOverTheLevelRequirement("felin", 1));
    }

    @Test
    void testIsOverTheLevelRequirementFalse() {
        userService.getUserByUsername("felin").setCurrentHeroId(heroRepository.findHeroByHeroName("Felixi").getId());
        Assertions.assertFalse(serviceToTest.isOverTheLevelRequirement("felin", 5));
    }

    @Test
    void testIsOverTheLevelRequirementThrowsUserNotFoundException() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> serviceToTest.isOverTheLevelRequirement("made_up_username", 5),
                "User with username made_up_username does not exist.");
    }

    @Test
    void testIsOverTheLevelRequirementThrowsHeroNotFoundException() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> serviceToTest.isOverTheLevelRequirement("felin", 5),
                "There is no hero with such id.");
    }

    @Test
    void testCreateNewHero() {
        UserEntity user = serviceToTest.getUserByUsername("felin");
        Assertions.assertNull(user.getCurrentHeroId());

        serviceToTest.createNewHero(
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

        serviceToTest.createNewHero(
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

        serviceToTest.createNewHero(
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
}
