package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.SkillEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.enumeration.SkillTypeEnum;
import game.destinyofthechosen.repository.SkillRepository;
import game.destinyofthechosen.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillEntity findByNameAndLevel(String skillName, Integer skillLevel) {
        return skillRepository.findBySkillNameAndLevel(skillName, skillLevel)
                .orElseThrow(() -> new ObjectNotFoundException("There is no skill with " + skillName + " name."));
    }

    @Override
    public SkillEntity findByRoleAndLevel(HeroRoleEnum heroRole, int skillLevel) {
        return skillRepository.findByRoleAndLevel(heroRole, skillLevel)
                .orElseThrow(() -> new ObjectNotFoundException("There is no skill with " + skillLevel + " level."));
    }

    @Override
    public void initialize() {
        if (skillRepository.count() != 0) return;

        List<SkillEntity> skillEntities = List.of(
                new SkillEntity("Shield Bash", "https://res.cloudinary.com/felin/image/upload/v1638570318/DestinyOfTheChosen/skills/ShieldBash.png",
                        HeroRoleEnum.WARRIOR, SkillTypeEnum.IMMOBILIZE, 30, 1, 11, 2,
                        "Uses the shield to perform a basic attack, dealing damage and stunning the target."),
                new SkillEntity("Double Arrow", "https://res.cloudinary.com/felin/image/upload/v1638047944/DestinyOfTheChosen/skills/DoubleArrow.jpg",
                        HeroRoleEnum.HUNTER, SkillTypeEnum.DAMAGE, 106, 1, 12, 1,
                        "Shoots two arrows, damaging the enemy."),
                new SkillEntity("Fireball", "https://res.cloudinary.com/felin/image/upload/v1638047833/DestinyOfTheChosen/skills/Fireball.png",
                        HeroRoleEnum.MAGE, SkillTypeEnum.DAMAGE, 74, 1, 38, 1,
                        "Shoots a fireball, damaging the enemy."),
                new SkillEntity("Shield Throw", "https://res.cloudinary.com/felin/image/upload/v1638047887/DestinyOfTheChosen/skills/ShieldThrow.jpg",
                        HeroRoleEnum.WARRIOR, SkillTypeEnum.DAMAGE, 72, 5, 17, 1,
                        "Throwing your shield, damaging the enemy."),
                new SkillEntity("Dark Spike Trap", "https://res.cloudinary.com/felin/image/upload/v1638572786/DestinyOfTheChosen/skills/DarkSpikeTrap.jpg",
                        HeroRoleEnum.HUNTER, SkillTypeEnum.IMMOBILIZE, 45, 5, 24, 2,
                        "Places a trap, immobilizing the enemy."),
                new SkillEntity("Light Beam", "https://res.cloudinary.com/felin/image/upload/v1638617881/DestinyOfTheChosen/skills/LightBeam.png",
                        HeroRoleEnum.MAGE, SkillTypeEnum.DAMAGE, 263, 5, 220, 3,
                        "Shoots out a light beam dealing massive damage."),
                new SkillEntity("Shield Bash", "https://res.cloudinary.com/felin/image/upload/v1638570318/DestinyOfTheChosen/skills/ShieldBash.png",
                        HeroRoleEnum.WARRIOR, SkillTypeEnum.IMMOBILIZE, 82, 10, 19, 2,
                        "Uses the shield to perform a basic attack, dealing damage and stunning the target."),
                new SkillEntity("Double Arrow", "https://res.cloudinary.com/felin/image/upload/v1638047944/DestinyOfTheChosen/skills/DoubleArrow.jpg",
                        HeroRoleEnum.HUNTER, SkillTypeEnum.DAMAGE, 242, 10, 18, 1,
                        "Shoots two arrows, dealing damage."),
                new SkillEntity("Fireball", "https://res.cloudinary.com/felin/image/upload/v1638047833/DestinyOfTheChosen/skills/Fireball.png",
                        HeroRoleEnum.MAGE, SkillTypeEnum.DAMAGE, 232, 10, 275, 1,
                        "Shoots a fireball, dealing damage."),
                new SkillEntity("ThousandCuts", "https://res.cloudinary.com/felin/image/upload/v1638635690/DestinyOfTheChosen/skills/ThousandCuts.png",
                        HeroRoleEnum.WARRIOR, SkillTypeEnum.DAMAGE, 322, 14, 35, 4,
                        "Warrior becomes enraged and slashes rapidly, dealing massive damage."),
                new SkillEntity("Sonic Arrow", "https://res.cloudinary.com/felin/image/upload/v1638635640/DestinyOfTheChosen/skills/SonicArrow.jpg",
                        HeroRoleEnum.HUNTER, SkillTypeEnum.DAMAGE, 362, 14, 31, 3,
                        "Shoots a lighting fast arrow, dealing massive damage."),
                new SkillEntity("Flash Heal", "https://res.cloudinary.com/felin/image/upload/v1638570434/DestinyOfTheChosen/skills/FlashHeal.jpg",
                        HeroRoleEnum.MAGE, SkillTypeEnum.HEAL, 218, 14, 350, 4,
                        "Light heal."),
                new SkillEntity("Shield Throw", "https://res.cloudinary.com/felin/image/upload/v1638047887/DestinyOfTheChosen/skills/ShieldThrow.jpg",
                        HeroRoleEnum.WARRIOR, SkillTypeEnum.DAMAGE, 172, 17, 23, 1,
                        "Throwing your shield, damaging the enemy."),
                new SkillEntity("Dark Spike Trap", "https://res.cloudinary.com/felin/image/upload/v1638572786/DestinyOfTheChosen/skills/DarkSpikeTrap.jpg",
                        HeroRoleEnum.HUNTER, SkillTypeEnum.IMMOBILIZE, 322, 17, 22, 2,
                        "Places a trap, immobilizing the enemy."),
                new SkillEntity("Light Beam", "https://res.cloudinary.com/felin/image/upload/v1638617881/DestinyOfTheChosen/skills/LightBeam.png",
                        HeroRoleEnum.MAGE, SkillTypeEnum.DAMAGE, 463, 17, 420, 3,
                        "Shoots out a light beam dealing massive damage.")
        );

        skillRepository.saveAll(skillEntities);
    }
}
