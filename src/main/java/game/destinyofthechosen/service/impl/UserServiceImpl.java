package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.HeroEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addNewHero(HeroEntity newHeroEntity, String userUsername) {
        UserEntity userEntity = userRepository.findByUsername(userUsername)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("User with username %s does not exist.", userUsername)));

        userEntity.addNewHero(newHeroEntity);
        userRepository.save(userEntity);
    }

    @Override
    public void register(UserRegisterServiceModel userModel) {

        if (userRepository.existsByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())) {
            throw new IllegalArgumentException("Username is taken.");
        }

        UserEntity user = new UserEntity(
                userModel.getUsername(),
                passwordEncoder.encode(userModel.getRawPassword()),
                userModel.getEmail(),
                Set.of(userRoleService.findByUserRole(UserRoleEnum.USER))
        );

        userRepository.save(user);
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
