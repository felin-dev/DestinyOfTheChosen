package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;

import java.util.UUID;

public interface HeroService {

    void createNewHero(HeroCreationServiceModel heroModel, String userId);

    boolean isHeroNameFree(String heroName);

    HeroEntity findHeroById(UUID id);

    void deleteById(UUID id);

    void gainExperience(UUID id, Integer experience);

    boolean isOverTheLevelRequirement(String username, Integer levelRequirement);

    void initialize();
}
