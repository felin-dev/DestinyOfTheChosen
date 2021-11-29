package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.SkillEntity;

public interface SkillService {

    SkillEntity findByNameAndLevel(String skillName, Integer skillLevel);

    void initialize();
}
