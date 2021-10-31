package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.UserRoleEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;

public interface UserRoleService {

    void initialize();

    UserRoleEntity findByUserRole(UserRoleEnum userRole);
}
