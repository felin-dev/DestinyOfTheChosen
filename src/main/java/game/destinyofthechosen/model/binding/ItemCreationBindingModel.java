package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.enumeration.ItemTypeEnum;
import game.destinyofthechosen.model.enumeration.StatEnum;
import game.destinyofthechosen.model.validator.NotNullFile;
import game.destinyofthechosen.model.validator.UniqueItemName;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.LinkedHashMap;
import java.util.Map;

public class ItemCreationBindingModel {

    @UniqueItemName
    @NotBlank(message = "Item name is required and must not be blank.")
    @Length(min = 2, max = 16, message = "Item name should be between 2 and 16 characters.")
    private String itemName;

    @NotNull(message = "Please select a type of the item.")
    private ItemTypeEnum type;

    private Map<StatEnum, Integer> stats = new LinkedHashMap<>();

    @NotNullFile
    private MultipartFile image;

    @NotNull(message = "Level requirement is required.")
    @Positive(message = "Level requirement must be a positive number.")
    private Integer levelRequirement;

    public String getItemName() {
        return itemName;
    }

    public ItemCreationBindingModel setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemTypeEnum getType() {
        return type;
    }

    public ItemCreationBindingModel setType(ItemTypeEnum type) {
        this.type = type;
        return this;
    }

    public Map<StatEnum, Integer> getStats() {
        return stats;
    }

    public ItemCreationBindingModel setStats(Map<StatEnum, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public ItemCreationBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ItemCreationBindingModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

    {
        for (StatEnum statName : StatEnum.values()) {
            stats.put(statName, 0);
        }
    }

    @Override
    public String toString() {
        return "ItemCreationBindingModel{" +
                "itemName='" + itemName + '\'' +
                ", type=" + type +
                ", stats=" + stats +
                ", image=" + image +
                ", levelRequirement=" + levelRequirement +
                '}';
    }
}
