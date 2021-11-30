package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.model.view.CombatStatusViewModel;
import game.destinyofthechosen.model.view.HeroCombatViewModel;
import game.destinyofthechosen.model.view.HeroSelectedViewModel;

import java.util.UUID;

public interface HeroService {

    CombatStatusViewModel castSkillOnEnemy(String username, String skillName);

    CombatStatusViewModel performAttackOnEnemy(String username);

    HeroSelectedViewModel getCurrentHero(String username);

    HeroCombatViewModel getCurrentHeroForCombat(String username);

    void heroIsOverTheLevelRequirementForThatZone();

    void updateCurrentHero(String username);

    void setCurrentEnemy(UUID id);

    CombatStatusViewModel resetCurrentEnemy();

    void createNewHero(HeroCreationServiceModel heroModel, String userId);

    void setCurrentHero(String username);

    HeroEntity findHeroById(UUID id);

    void deleteById(UUID id);

    boolean isOverTheLevelRequirement(String username, Integer levelRequirement);

    void initialize();

    boolean isHeroNameFree(String heroName);
}
