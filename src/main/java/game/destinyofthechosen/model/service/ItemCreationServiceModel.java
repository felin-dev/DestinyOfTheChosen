package game.destinyofthechosen.model.service;

import game.destinyofthechosen.model.enumeration.ItemTypeEnum;
import game.destinyofthechosen.model.enumeration.StatEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemCreationServiceModel {
    private String itemName;
    private ItemTypeEnum type;
    private Map<StatEnum, Integer> stats = new LinkedHashMap<>();
    private MultipartFile image;
    private Integer levelRequirement;

    public String getItemName() {
        return itemName;
    }

    public ItemCreationServiceModel setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemTypeEnum getType() {
        return type;
    }

    public ItemCreationServiceModel setType(ItemTypeEnum type) {
        this.type = type;
        return this;
    }

    public Map<StatEnum, Integer> getStats() {
        return stats;
    }

    public ItemCreationServiceModel setStats(Map<StatEnum, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public ItemCreationServiceModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ItemCreationServiceModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }
}
