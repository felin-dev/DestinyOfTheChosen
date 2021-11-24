package game.destinyofthechosen.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnemyCreationServiceModel {

    private String enemyName;
    private MultipartFile image;
    private Integer level;
    private Integer experience;
    private Integer health;
    private Integer attack;
    private Integer goldDropUpperThreshold;
    private Integer goldDropLowerThreshold;
    private List<UUID> dropList = new ArrayList<>();
    private String zoneName;

    public String getEnemyName() {
        return enemyName;
    }

    public EnemyCreationServiceModel setEnemyName(String enemyName) {
        this.enemyName = enemyName;
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

    public List<UUID> getDropList() {
        return dropList;
    }

    public EnemyCreationServiceModel setDropList(List<UUID> dropList) {
        this.dropList = dropList;
        return this;
    }

    public String getZoneName() {
        return zoneName;
    }

    public EnemyCreationServiceModel setZoneName(String zoneName) {
        this.zoneName = zoneName;
        return this;
    }

    @Override
    public String toString() {
        return "EnemyCreationServiceModel{" +
                "name='" + enemyName + '\'' +
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
