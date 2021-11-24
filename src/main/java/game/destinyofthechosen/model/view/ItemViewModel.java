package game.destinyofthechosen.model.view;

import game.destinyofthechosen.model.enumeration.ItemTypeEnum;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ItemViewModel {

    private UUID id;
    private String itemName;
    private ItemTypeEnum type;
    private Map<String, Integer> stats = new LinkedHashMap<>();
    private String imageUrl;
    private Integer levelRequirement;

    public UUID getId() {
        return id;
    }

    public ItemViewModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemViewModel setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemTypeEnum getType() {
        return type;
    }

    public ItemViewModel setType(ItemTypeEnum type) {
        this.type = type;
        return this;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public ItemViewModel setStats(Map<String, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ItemViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ItemViewModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }
}
