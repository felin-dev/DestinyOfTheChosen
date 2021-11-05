package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;

import java.util.UUID;

public interface UserService {

    void register(UserRegisterServiceModel userModel);

    boolean isUsernameFree(String username);

    boolean isEmailFree(String email);

    void addNewHero(UserEntity userEntity, HeroEntity newHeroEntity, String userUsername);

    UserEntity getUserByName(String userUsername);
}
