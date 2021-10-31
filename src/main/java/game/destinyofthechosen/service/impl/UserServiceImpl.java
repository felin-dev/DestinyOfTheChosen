package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserRegisterServiceModel userModel) {

        if (userRepository.existsByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())) {
            throw new IllegalArgumentException();
        }

        //TODO
    }

    @Override
    public boolean isUsernameFree(String username) {
        return userRepository.existsByUsername(username);
    }
}
