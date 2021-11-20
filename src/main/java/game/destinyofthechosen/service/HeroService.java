package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;

import java.util.UUID;

public interface HeroService {

    void createNewHero(HeroCreationServiceModel heroModel, String userId);

    boolean isHeroNameFree(String heroName);

    HeroEntity getById(UUID id);

    void deleteById(UUID id);
}
