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
    public void initialize() {
        if (skillRepository.count() != 0) return;

        List<SkillEntity> skillEntities = List.of(
                new SkillEntity("Shield Bash", "https://res.cloudinary.com/felin/image/upload/v1638047887/DestinyOfTheChosen/skills/ShieldBash.jpg",
                        HeroRoleEnum.WARRIOR, SkillTypeEnum.IMMOBILIZE, 10, 1, 55, 2,
                        "Uses the shield to perform a basic attack, dealing damage and stunning the target."),
                new SkillEntity("Double Arrow", "https://res.cloudinary.com/felin/image/upload/v1638047944/DestinyOfTheChosen/skills/DoubleArrow.jpg",
                        HeroRoleEnum.HUNTER, SkillTypeEnum.DAMAGE, 20, 1, 45, 1,
                        "Shoots two arrows, dealing damage."),
                new SkillEntity("Fireball", "https://res.cloudinary.com/felin/image/upload/v1638047833/DestinyOfTheChosen/skills/Fireball.png",
                        HeroRoleEnum.MAGE, SkillTypeEnum.DAMAGE, 14, 1, 88, 1,
                        "Shoots a fireball, dealing damage.")
        );

        skillRepository.saveAll(skillEntities);
    }
}