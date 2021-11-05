package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.repository.HeroRepository;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final UserService userService;

    public HeroServiceImpl(HeroRepository heroRepository, UserService userService) {
        this.heroRepository = heroRepository;
        this.userService = userService;
    }

    @Override
    public void createNewHero(HeroCreationServiceModel heroModel, String userUsername) {

        if (heroRepository.existsByName(heroModel.getName())) {
            throw new IllegalArgumentException("Hero name is taken.");
        }

        UserEntity userEntity = userService.getUserByName(userUsername);

        HeroEntity newHeroEntity = createNewHeroEntity(heroModel);
        newHeroEntity.setUser(userEntity);
        heroRepository.save(newHeroEntity);

        userService.addNewHero(userEntity, newHeroEntity, userUsername);
    }

    private HeroEntity createNewHeroEntity(HeroCreationServiceModel heroModel) {

        HeroEntity heroEntity = new HeroEntity(heroModel.getName(), heroModel.getHeroRole());

        switch (heroEntity.getHeroRole()) {
            case HUNTER -> createHunter(heroEntity);
            case WARRIOR -> createWarrior(heroEntity);
            case MAGE -> createMage(heroEntity);
        }

        return heroEntity;
    }

    private void createHunter(HeroEntity hunter) {
        hunter
                .setBaseHealth(120)     // vitality x20
                .setBaseMana(80)        // energy x20
                .setBaseAttack(25)      // dexterity x2 + strength x1
                .setBaseMagicPower(4)   // energy x1
                .setBaseDefense(10)     // base defense increases with items
                .setBaseDexterity(10)
                .setBaseStrength(5)
                .setBaseEnergy(4)
                .setBaseVitality(6)
                .setImageUrl("hunter.jpg");
    }

    private void createWarrior(HeroEntity warrior) {
        warrior
                .setBaseHealth(180)     // vitality x20
                .setBaseMana(100)       // energy x20
                .setBaseAttack(17)      // strength x2 + dexterity x1
                .setBaseMagicPower(5)   // energy x1
                .setBaseDefense(12)     // base defense increases with items
                .setBaseDexterity(5)
                .setBaseStrength(6)
                .setBaseEnergy(5)
                .setBaseVitality(9)
                .setImageUrl("warrior.jpg");
    }

    private void createMage(HeroEntity mage) {
        mage
                .setBaseHealth(100)     // vitality x20
                .setBaseMana(240)       // energy x20
                .setBaseAttack(8)       // dexterity x1 + strength x1
                .setBaseMagicPower(24)  // energy x2
                .setBaseDefense(8)      // base defense increases with items
                .setBaseDexterity(4)
                .setBaseStrength(4)
                .setBaseEnergy(12)
                .setBaseVitality(5)
                .setImageUrl("mage.jpg");
    }

    @Override
    public boolean isHeroNameFree(String heroName) {
        return !heroRepository.existsByName(heroName);
    }
}
