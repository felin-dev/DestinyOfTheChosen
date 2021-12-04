package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.SkillEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

public interface SkillService {

    SkillEntity findByNameAndLevel(String skillName, Integer skillLevel);

    void initialize();

    SkillEntity findByRoleAndLevel(HeroRoleEnum heroRole, int level);
}
