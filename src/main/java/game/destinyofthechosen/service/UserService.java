package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.service.UsernameServiceModel;
import game.destinyofthechosen.model.view.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void register(UserRegisterServiceModel userModel);

    boolean isUsernameFree(String username);

    boolean isEmailFree(String email);

    void selectNewHero(String username, HeroSelectServiceModel selectedHero);

    UserEntity getUserByUsername(String username);

    boolean userHasNoSelectedHero(String username);

    UserHeroSelectViewModel getUserWithOwnedHeroes(String username);

    List<ItemViewModel> getAllOwnedItems(String username);

    List<HeroSelectedViewModel> getHeroesViewModel(String username);

    void transferItemToHero(String username, String heroName, UUID itemId);

    void throwItem(String username, UUID weaponId);

    UserShopViewModel getUserShopView(String username);

    String buyChest(String username);

    boolean ownsThisHero(String username, UUID selectedHeroId);

    void deleteHero(String name, HeroSelectServiceModel map);

    void makeUserAnAdmin(String adminName, UsernameServiceModel usernameServiceModel);

    void removeAdminRoleFromUser(String adminName, UsernameServiceModel usernameServiceModel);

    void initialize();
}
