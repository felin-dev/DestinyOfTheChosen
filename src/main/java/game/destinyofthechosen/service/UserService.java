package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;

public interface UserService {

    void register(UserRegisterServiceModel userModel);

    boolean isUsernameFree(String username);

    boolean isEmailFree(String email);

    void addNewHero(HeroEntity newHeroEntity, String userUsername);
}
