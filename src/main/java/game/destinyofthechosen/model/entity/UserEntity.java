package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.UserRoleEnum;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 35)
    private String username;

    @Column(nullable = false, length = 40)
    private String password;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @ManyToMany
    private Set<UserRoleEntity> userRoles;

    private UUID currentHeroId;

    @OneToMany(mappedBy = "user")
    private List<HeroEntity> heroes;

    private Integer gold;

    @OneToMany(mappedBy = "user")
    private List<ItemEntity> stash;

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserEntity setUserRoles(Set<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public UUID getCurrentHeroId() {
        return currentHeroId;
    }

    public UserEntity setCurrentHeroId(UUID currentHeroId) {
        this.currentHeroId = currentHeroId;
        return this;
    }

    public List<HeroEntity> getHeroes() {
        return heroes;
    }

    public UserEntity setHeroes(List<HeroEntity> heroes) {
        this.heroes = heroes;
        return this;
    }

    public Integer getGold() {
        return gold;
    }

    public UserEntity setGold(Integer gold) {
        this.gold = gold;
        return this;
    }

    public List<ItemEntity> getStash() {
        return stash;
    }

    public UserEntity setStash(List<ItemEntity> stash) {
        this.stash = stash;
        return this;
    }
}
