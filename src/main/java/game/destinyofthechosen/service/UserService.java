package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.view.CombatStatusViewModel;
import game.destinyofthechosen.model.view.HeroCombatViewModel;
import game.destinyofthechosen.model.view.HeroSelectedViewModel;
import game.destinyofthechosen.model.view.UserHeroSelectViewModel;

import java.util.UUID;

public interface UserService {

    void register(UserRegisterServiceModel userModel);

    boolean isUsernameFree(String username);

    boolean isEmailFree(String email);

    void selectNewHero(String username, HeroSelectServiceModel selectedHero);

    UserEntity getUserByUsername(String username);

    boolean userHasNoSelectedHero(String username);

    UserHeroSelectViewModel getUserWithOwnedHeroes(String username);

    boolean ownsThisHero(String username, UUID selectedHeroId);

    void deleteHero(String name, HeroSelectServiceModel map);

    void initialize();
}
