package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.view.HeroInfoViewModel;
import game.destinyofthechosen.model.view.UserHeroSelectViewModel;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.modelmapper.ModelMapper;
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
    private final HeroService heroService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserServiceImpl securityUserService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, HeroService heroService, UserRoleService userRoleService, PasswordEncoder passwordEncoder, SecurityUserServiceImpl securityUserService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.heroService = heroService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.securityUserService = securityUserService;
        this.modelMapper = modelMapper;
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
                "felin",
                passwordEncoder.encode("testtest"),
                "felin@gmail.com",
                Set.of(userRoleService.findByUserRole(UserRoleEnum.ADMIN),
                        userRoleService.findByUserRole(UserRoleEnum.USER))
        ), new UserEntity(
                "felixi",
                passwordEncoder.encode("testtest"),
                "felixi@gmail.com",
                Set.of(userRoleService.findByUserRole(UserRoleEnum.USER))
        ));

        userRepository.saveAll(userEntities);
    }
}
