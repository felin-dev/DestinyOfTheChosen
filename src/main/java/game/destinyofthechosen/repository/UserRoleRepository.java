package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.RoleEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByUserRole(UserRoleEnum userRole);
}
