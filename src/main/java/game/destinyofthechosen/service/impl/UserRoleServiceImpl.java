package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.UserRoleEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.repository.UserRoleRepository;
import game.destinyofthechosen.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initialize() {
        if (userRoleRepository.count() != 0) return;

        Arrays.stream(UserRoleEnum.values())
                .forEach(userRoleEnum ->
                        userRoleRepository.save(
                                new UserRoleEntity().setUserRole(userRoleEnum)));
    }

    @Override
    public UserRoleEntity findByUserRole(UserRoleEnum userRole) {
        return userRoleRepository.findByUserRole(userRole);
    }
}
