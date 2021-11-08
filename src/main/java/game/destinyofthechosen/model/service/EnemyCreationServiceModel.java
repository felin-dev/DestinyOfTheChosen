package game.destinyofthechosen.model.service;

import game.destinyofthechosen.model.enumeration.ItemNameEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class EnemyCreationServiceModel {

    private String name;
    private MultipartFile image;
    private Integer level;
    private Integer experience;
    private Integer health;
    private Integer attack;
    private Integer goldDropUpperThreshold;
    private Integer goldDropLowerThreshold;
    private List<ItemNameEnum> dropList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public EnemyCreationServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public EnemyCreationServiceModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public EnemyCreationServiceModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public EnemyCreationServiceModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public EnemyCreationServiceModel setHealth(Integer health) {
        this.health = health;
        return this;
    }

    public Integer getAttack() {
        return attack;
    }

    public EnemyCreationServiceModel setAttack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public Integer getGoldDropUpperThreshold() {
        return goldDropUpperThreshold;
    }

    public EnemyCreationServiceModel setGoldDropUpperThreshold(Integer goldDropUpperThreshold) {
        this.goldDropUpperThreshold = goldDropUpperThreshold;
        return this;
    }

    public Integer getGoldDropLowerThreshold() {
        return goldDropLowerThreshold;
    }

    public EnemyCreationServiceModel setGoldDropLowerThreshold(Integer goldDropLowerThreshold) {
        this.goldDropLowerThreshold = goldDropLowerThreshold;
        return this;
    }

    public List<ItemNameEnum> getDropList() {
        return dropList;
    }

    public EnemyCreationServiceModel setDropList(List<ItemNameEnum> dropList) {
        this.dropList = dropList;
        return this;
    }

    @Override
    public String toString() {
        return "EnemyCreationServiceModel{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", level=" + level +
                ", health=" + health +
                ", attack=" + attack +
                ", goldDropUpperThreshold=" + goldDropUpperThreshold +
                ", goldDropLowerThreshold=" + goldDropLowerThreshold +
                ", dropList=" + dropList +
                '}';
    }
}
