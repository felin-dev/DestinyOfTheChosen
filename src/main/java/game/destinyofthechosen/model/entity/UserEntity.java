package game.destinyofthechosen.model.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 35)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id")
    )
    private Set<RoleEntity> userRoles = new HashSet<>();

    private UUID currentHeroId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<HeroEntity> heroes = new ArrayList<>();

    @Column(nullable = false)
    private Integer gold = 0;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ItemEntity> stash = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String username, String password, String email, Set<RoleEntity> userRoles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRoles = userRoles;
    }

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

    public Set<RoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserEntity setUserRoles(Set<RoleEntity> userRoles) {
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

    public void addNewHero(HeroEntity newHero) {
        this.heroes.add(newHero);
    }
}
