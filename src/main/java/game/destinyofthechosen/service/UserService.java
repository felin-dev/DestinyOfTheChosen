package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.view.HeroSelectedViewModel;
import game.destinyofthechosen.model.view.UserHeroSelectViewModel;

public interface UserService {

    void register(UserRegisterServiceModel userModel);

    boolean isUsernameFree(String username);

    boolean isEmailFree(String email);

    void addNewHero(UserEntity userEntity, HeroEntity newHeroEntity);

    void selectNewHero(String username, HeroSelectServiceModel selectedHero);

    UserEntity getUserByUsername(String username);

    boolean userHasNoSelectedHero(String username);

    UserHeroSelectViewModel getUserWithOwnedHeroes(String username);

    boolean ownsThisHero(String username, HeroSelectServiceModel selectedHero);

    void deleteHero(String name, HeroSelectServiceModel map);

    HeroSelectedViewModel getCurrentHero(String username);

    boolean isOverTheLevelRequirement(String username, Integer levelRequirement);
}
