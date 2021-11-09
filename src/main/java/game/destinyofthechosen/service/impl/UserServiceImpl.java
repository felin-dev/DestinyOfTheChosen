package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserServiceImpl securityUserService;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, SecurityUserServiceImpl securityUserService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.securityUserService = securityUserService;
    }

    @Override
    public void addNewHero(UserEntity userEntity, HeroEntity newHeroEntity, String userUsername) {
        userEntity.addNewHero(newHeroEntity);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByName(String userUsername) {
        return userRepository.findByUsername(userUsername)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("User with username %s does not exist.", userUsername)));
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
