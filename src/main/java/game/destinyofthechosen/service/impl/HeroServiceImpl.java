package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.repository.HeroRepository;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.HeroService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class HeroServiceImpl implements HeroService {

    private static final String WARRIOR_IMAGE = "https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/warrior-f.png";
    private static final String HUNTER_IMAGE = "https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/hunter-f.png";
    private static final String MAGE_IMAGE = "https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/mage-f.png";
    private static final Map<Integer, Integer> LEVELING = new LinkedHashMap<>();

    private final HeroRepository heroRepository;
    private final UserRepository userRepository;

    public HeroServiceImpl(HeroRepository heroRepository, UserRepository userRepository) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void gainExperience(UUID id, Integer experience) {
        HeroEntity heroEntity = getById(id);
        heroEntity.setExperience(heroEntity.getExperience() + experience);
        checkIfHeroHasToLevelUp(heroEntity);

        heroRepository.save(heroEntity);
    }

    private void checkIfHeroHasToLevelUp(HeroEntity heroEntity) {
        Integer currentLevel = heroEntity.getLevel();
        if (currentLevel == 20) return;

        Integer experienceNeededForLevelingUp = LEVELING.get(currentLevel + 1);
        if (heroEntity.getExperience() >= experienceNeededForLevelingUp) heroLeveledUp(heroEntity);
    }

    private void heroLeveledUp(HeroEntity heroEntity) {
        heroEntity
                .levelUp();
    }

    @Override
    public void createNewHero(HeroCreationServiceModel heroModel, String username) {

        UserEntity userEntity = getUserByUsername(username);

        HeroEntity newHeroEntity = createNewHeroEntity(heroModel);
        newHeroEntity.setUser(userEntity);
        heroRepository.save(newHeroEntity);
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
                .setImageUrl(WARRIOR_IMAGE);
    }

    private void createHunter(HeroEntity hunter) {
        hunter
                .setBaseDefense(10)     // base defense increases with items and levels
                .setBaseDexterity(10)   // attack = dexterity x2
                .setBaseStrength(5)     // attack = strength x1
                .setBaseEnergy(4)       // mana = energy x20, magic power = energy x1
                .setBaseVitality(6)     // health = vitality x20
                .setImageUrl(HUNTER_IMAGE);
    }

    private void createMage(HeroEntity mage) {
        mage
                .setBaseDefense(8)      // base defense increases with items and levels
                .setBaseDexterity(4)    // attack = dexterity x1
                .setBaseStrength(4)     // attack = strength x1
                .setBaseEnergy(12)      // mana = energy x20, magic power = energy x2
                .setBaseVitality(5)     // health = vitality x20
                .setImageUrl(MAGE_IMAGE);
    }

    @Override
    public boolean isHeroNameFree(String heroName) {
        return !heroRepository.existsByHeroName(heroName);
    }

    @Override
    public HeroEntity getById(UUID id) {
        return heroRepository.findHeroById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no hero with such id."));
    }

    @Override
    public void deleteById(UUID id) {
        heroRepository.deleteById(id);
    }

    static {
        Integer experience = 1200;

        for (int i = 2; i <= 20; i++) {
            LEVELING.put(i, experience);
            experience += experience;
        }
    }
}
