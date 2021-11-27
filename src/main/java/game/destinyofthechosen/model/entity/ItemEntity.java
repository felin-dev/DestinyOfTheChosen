package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.ItemTypeEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class ItemEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 16)
    private String itemName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemTypeEnum type;

    @OneToMany(mappedBy = "item")
    private List<StatEntity> stats = new ArrayList<>();

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer levelRequirement;

    public ItemEntity() {
    }

    public ItemEntity(String itemName, ItemTypeEnum type, String imageUrl, Integer levelRequirement) {
        this.itemName = itemName;
        this.type = type;
        this.imageUrl = imageUrl;
        this.levelRequirement = levelRequirement;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemEntity setItemName(String itemName) {
        this.itemName = itemName;
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
}
