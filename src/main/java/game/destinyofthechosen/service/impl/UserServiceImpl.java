package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.RoleEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.service.UsernameServiceModel;
import game.destinyofthechosen.model.view.*;
import game.destinyofthechosen.repository.HeroRepository;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.ItemService;
import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HeroRepository heroRepository;
    private final ItemService itemService;
    private final HeroService heroService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserServiceImpl securityUserService;
    private final ModelMapper modelMapper;
    private final String adminUsername;
    private final String adminPassword;
    private final String adminEmail;
    private final String userUsername;
    private final String userPassword;
    private final String userEmail;

    public UserServiceImpl(UserRepository userRepository, HeroRepository heroRepository, ItemService itemService, HeroService heroService, UserRoleService userRoleService,
                           PasswordEncoder passwordEncoder, SecurityUserServiceImpl securityUserService,
                           ModelMapper modelMapper,
                           @Value("${admin.username}") String adminUsername,
                           @Value("${admin.password}") String adminPassword,
                           @Value("${admin.email}") String adminEmail,
                           @Value("${user.username}") String userUsername,
                           @Value("${user.password}") String userPassword,
                           @Value("${user.email}") String userEmail) {
        this.userRepository = userRepository;
        this.heroRepository = heroRepository;
        this.itemService = itemService;
        this.heroService = heroService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.securityUserService = securityUserService;
        this.modelMapper = modelMapper;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.adminEmail = adminEmail;
        this.userUsername = userUsername;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    @Override
    @Transactional
    public void selectNewHero(String username, HeroSelectServiceModel selectedHeroId) {
        UserEntity userEntity = getUserByUsername(username);

        userEntity.setCurrentHeroId(selectedHeroId.getId());
        userRepository.save(userEntity);

        heroService.setCurrentHero(username);
    }

    @Override
    public boolean userHasNoSelectedHero(String username) {
        return getUserByUsername(username).getCurrentHeroId() == null;
    }

    @Override
    @Transactional
    public void deleteHero(String username, HeroSelectServiceModel heroModel) {
        UserEntity user = getUserByUsername(username);
        if (user.getCurrentHeroId() != null && user.getCurrentHeroId().equals(heroModel.getId()))
            user.setCurrentHeroId(null);

        user.getHeroes().remove(heroService.getHeroById(heroModel.getId()));

        userRepository.save(user);
        heroService.deleteById(heroModel.getId());
    }

    @Override
    @Transactional
    public boolean ownsThisHero(String username, UUID selectedHeroId) {
        return getUserByUsername(username)
                .getHeroes()
                .stream()
                .anyMatch(hero -> hero.getId().equals(selectedHeroId));
    }

    @Override
    @Transactional
    public UserHeroSelectViewModel getUserWithOwnedHeroes(String username) {
        UserEntity user = getUserByUsername(username);
        HeroEntity heroEntity = user.getCurrentHeroId() == null ?
                new HeroEntity() : heroService.
                getHeroById(user.getCurrentHeroId());

        return new UserHeroSelectViewModel()
                .setCurrentHero(modelMapper.map(heroEntity, HeroInfoViewModel.class))
                .setHeroes(user
                        .getHeroes()
                        .stream()
                        .sorted((h1, h2) -> h2.getLevel() - h1.getLevel())
                        .map(hero -> modelMapper.map(hero, HeroInfoViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public List<ItemViewModel> getAllOwnedItems(String username) {
        return getUserByUsername(username)
                .getStash()
                .stream()
                .map(itemEntity -> itemService.getItemViewById(itemEntity.getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HeroSelectedViewModel> getHeroesViewModel(String username) {
        return getUserByUsername(username)
                .getHeroes()
                .stream()
                .map(heroEntity -> new HeroSelectedViewModel()
                        .setName(heroEntity.getHeroName())
                        .setHeroRole(heroEntity.getHeroRole())
                        .setLevel(heroEntity.getLevel()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void transferItemToHero(String username, String heroName, UUID itemId) {
        UserEntity userEntity = getUserByUsername(username);
        HeroEntity heroEntity = userEntity
                .getHeroes()
                .stream()
                .filter(hero -> hero.getHeroName().equals(heroName))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("User does not have hero with name: " + heroName + "."));

        ItemEntity itemEntity = userEntity
                .getStash()
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("User does not have item with id: " + itemId + "."));

        userEntity.getStash().remove(itemEntity);
        userRepository.save(userEntity);

        heroEntity.addItem(itemEntity);
        heroRepository.save(heroEntity);
    }

    @Override
    @Transactional
    public void throwItem(String username, UUID weaponId) {
        UserEntity userEntity = getUserByUsername(username);
        ItemEntity itemEntity = itemService.getItemById(weaponId);
        if (userEntity.getStash().contains(itemEntity)) userEntity.throwItem(itemEntity);
    }

    @Override
    @Transactional
    public UserShopViewModel getUserShopView(String username) {
        UserEntity userEntity = getUserByUsername(username);
        HeroSelectedViewModel currentHero = heroService.getCurrentHero(username);

        return new UserShopViewModel()
                .setGold(userEntity.getGold())
                .setCurrentHeroLevel(currentHero.getLevel());
    }

    @Override
    @Transactional
    public String buyChest(String username) {
        UserEntity userEntity = getUserByUsername(username);
        HeroSelectedViewModel currentHero = heroService.getCurrentHero(username);

        int chestGoldCost = currentHero.getLevel() * 30;
        if (userEntity.getGold() < chestGoldCost) return "You do not have enough gold.";

        userEntity.spendGold(chestGoldCost);
        ItemEntity itemEntity = itemService.getRandomItemInLevelRequirementRange(currentHero.getLevel());

        userEntity.addNewItem(itemEntity);
        userRepository.save(userEntity);
        return "You received: " + itemEntity.getItemName();
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with username %s does not exist.", username)));
    }

    @Override
    public void register(UserRegisterServiceModel userModel) {

        UserEntity user = new UserEntity(
                userModel.getUsername(),
                passwordEncoder.encode(userModel.getRawPassword()),
                userModel.getEmail(),
                Set.of(userRoleService.findByUserRole(UserRoleEnum.USER))
        );

        userRepository.save(user);

        login(user);
    }

    private void login(UserEntity user) {

        UserDetails userDetails = securityUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                user.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    @Transactional
    public void makeUserAnAdmin(String adminName, UsernameServiceModel usernameServiceModel) {
        boolean notAdmin = getUserByUsername(adminName)
                .getUserRoles()
                .stream()
                .noneMatch(roleEntity -> roleEntity.getUserRole().equals(UserRoleEnum.ADMIN));

        if (notAdmin) throw new UserHasNoPermissionToAccessException("Admin role is required to make this operation.");

        UserEntity user = getUserByUsername(usernameServiceModel.getUsername());
        RoleEntity adminRole = userRoleService.findByUserRole(UserRoleEnum.ADMIN);
        userRepository.save(user.addNewRole(adminRole));
    }

    @Override
    @Transactional
    public void removeAdminRoleFromUser(String adminName, UsernameServiceModel usernameServiceModel) {
        boolean notAdmin = getUserByUsername(adminName)
                .getUserRoles()
                .stream()
                .noneMatch(roleEntity -> roleEntity.getUserRole().equals(UserRoleEnum.ADMIN));

        if (notAdmin) throw new UserHasNoPermissionToAccessException("Admin role is required to make this operation.");

        UserEntity user = getUserByUsername(usernameServiceModel.getUsername());
        RoleEntity adminRole = userRoleService.findByUserRole(UserRoleEnum.ADMIN);
        userRepository.save(user.removeRole(adminRole));
    }

    @Override
    public boolean isUsernameFree(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailFree(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public void initialize() {
        if (userRepository.count() != 0) return;

        List<UserEntity> userEntities = List.of(new UserEntity(
                adminUsername,
                passwordEncoder.encode(adminPassword),
                adminEmail,
                Set.of(userRoleService.findByUserRole(UserRoleEnum.ADMIN),
                        userRoleService.findByUserRole(UserRoleEnum.USER))
        ), new UserEntity(
                userUsername,
                passwordEncoder.encode(userPassword),
                userEmail,
                Set.of(userRoleService.findByUserRole(UserRoleEnum.USER))
        ));

        userRepository.saveAll(userEntities);
    }

    @Override
    public void addDailyMoney() {
        List<UserEntity> allUsers = userRepository.findAll();
        allUsers.forEach(userEntity -> {
            userEntity.addGold(1000);
            userRepository.save(userEntity);
        });
    }
}
