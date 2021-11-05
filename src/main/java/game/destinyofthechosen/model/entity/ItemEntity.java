package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.ItemTypeEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
public class ItemEntity extends BaseEntity {

    @Column(nullable = false, length = 35)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemTypeEnum type;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<StatEntity> stats = new ArrayList<>();

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer levelRequirement;

    @ManyToOne
    private HeroEntity hero;

    @ManyToOne
    private UserEntity user;

    public String getName() {
        return name;
    }

    public ItemEntity setName(String name) {
        this.name = name;
        return this;
    }

    public ItemTypeEnum getType() {
        return type;
    }

    public ItemEntity setType(ItemTypeEnum type) {
        this.type = type;
        return this;
    }

    public List<StatEntity> getStats() {
        return stats;
    }

    public ItemEntity setStats(List<StatEntity> stats) {
        this.stats = stats;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ItemEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ItemEntity setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

    public HeroEntity getHero() {
        return hero;
    }

    public ItemEntity setHero(HeroEntity hero) {
        this.hero = hero;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public ItemEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
