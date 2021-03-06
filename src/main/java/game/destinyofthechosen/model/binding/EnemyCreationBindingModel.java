package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.validator.NotNullFile;
import game.destinyofthechosen.model.validator.UniqueEnemyName;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnemyCreationBindingModel {

    @UniqueEnemyName
    @NotBlank(message = "Enemy name is required and must not be blank.")
    @Length(min = 2, max = 16, message = "Enemy name should be between 2 and 16 characters.")
    private String enemyName;

    @NotNullFile
    private MultipartFile image;

    @NotNull(message = "Level is required.")
    @Positive(message = "The enemy level must be a positive number.")
    private Integer level;

    @NotNull(message = "Experience is required.")
    @Positive(message = "The enemy experience must be a positive number.")
    private Integer experience;

    @NotNull(message = "Health is required.")
    @Positive(message = "The enemy health must be a positive number.")
    private Integer health;

    @NotNull(message = "Attack is required.")
    @Positive(message = "The enemy attack must be a positive number.")
    private Integer attack;

    @NotNull(message = "Gold upper threshold is required.")
    @Positive(message = "The gold upper threshold must be a positive number.")
    private Integer goldDropUpperThreshold;

    @NotNull(message = "Gold lower threshold is required.")
    @Positive(message = "The gold lower threshold must be a positive number.")
    private Integer goldDropLowerThreshold;

    private List<UUID> dropList = new ArrayList<>();

    @NotNull
    private String zoneName;

    public String getEnemyName() {
        return enemyName;
    }

    public EnemyCreationBindingModel setEnemyName(String enemyName) {
        this.enemyName = enemyName;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public EnemyCreationBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public EnemyCreationBindingModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public EnemyCreationBindingModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public EnemyCreationBindingModel setHealth(Integer health) {
        this.health = health;
        return this;
    }

    public Integer getAttack() {
        return attack;
    }

    public EnemyCreationBindingModel setAttack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public Integer getGoldDropUpperThreshold() {
        return goldDropUpperThreshold;
    }

    public EnemyCreationBindingModel setGoldDropUpperThreshold(Integer goldDropUpperThreshold) {
        this.goldDropUpperThreshold = goldDropUpperThreshold;
        return this;
    }

    public Integer getGoldDropLowerThreshold() {
        return goldDropLowerThreshold;
    }

    public EnemyCreationBindingModel setGoldDropLowerThreshold(Integer goldDropLowerThreshold) {
        this.goldDropLowerThreshold = goldDropLowerThreshold;
        return this;
    }

    public List<UUID> getDropList() {
        return dropList;
    }

    public EnemyCreationBindingModel setDropList(List<UUID> dropList) {
        this.dropList = dropList;
        return this;
    }

    public String getZoneName() {
        return zoneName;
    }

    public EnemyCreationBindingModel setZoneName(String zoneName) {
        this.zoneName = zoneName;
        return this;
    }
}
