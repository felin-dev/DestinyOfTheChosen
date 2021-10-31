package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRoleEntity setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
