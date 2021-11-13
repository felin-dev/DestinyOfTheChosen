package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.model.view.HeroSelectViewModel;
import game.destinyofthechosen.model.view.UserHeroSelectViewModel;
import game.destinyofthechosen.repository.HeroRepository;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HeroRepository heroRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserServiceImpl securityUserService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, HeroRepository heroRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, SecurityUserServiceImpl securityUserService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.heroRepository = heroRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.securityUserService = securityUserService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addNewHero(UserEntity userEntity, HeroEntity newHeroEntity) {
        userEntity.addNewHero(newHeroEntity);
        userRepository.save(userEntity);
    }

    @Override
    public UserHeroSelectViewModel getUserWithOwnedHeroes(String username) {
        UserEntity user = getUserByUsername(username);

        return new UserHeroSelectViewModel()
                .setCurrentHero(modelMapper.map(heroRepository.
                        findHeroById(user.getCurrentHeroId()).orElse(new HeroEntity()), HeroSelectViewModel.class))
                .setHeroes(user
                        .getHeroes()
                        .stream()
                        .sorted((h1, h2) -> h2.getLevel() - h1.getLevel())
                        .map(hero -> modelMapper.map(hero, HeroSelectViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Override
    public boolean userHasNoSelectedHero(String username) {
        return getUserByUsername(username).getCurrentHeroId() == null;
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
}
