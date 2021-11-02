package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.RoleEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;

public interface UserRoleService {

    void initialize();

    RoleEntity findByUserRole(UserRoleEnum userRole);
}
