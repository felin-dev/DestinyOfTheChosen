package game.destinyofthechosen.service;

import game.destinyofthechosen.model.service.HeroCreationServiceModel;

public interface HeroService {

    void createNewHero(HeroCreationServiceModel heroModel, String userId);

    boolean isHeroNameFree(String heroName);
}
