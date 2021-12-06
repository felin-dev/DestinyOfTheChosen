package game.destinyofthechosen.model.view;

import java.util.List;
import java.util.UUID;

public class EnemyViewModel {

    private UUID id;
    private String name;
    private String imageUrl;
    private Integer level;
    private Integer experience;
    private Integer health;
    private Integer currentHealth;
    private Integer attack;
    private Integer goldDropUpperThreshold;
    private Integer goldDropLowerThreshold;
    private String zoneImageUrl;
    private String zoneName;
    private Integer zoneLevelRequirement;
    private List<ItemViewModel> dropList;

    public UUID getId() {
        return id;
    }

    public EnemyViewModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EnemyViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EnemyViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public EnemyViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public EnemyViewModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public EnemyViewModel setHealth(Integer health) {
        this.health = health;
        return this;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public EnemyViewModel setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public Integer getAttack() {
        return attack;
    }

    public EnemyViewModel setAttack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public String getZoneImageUrl() {
        return zoneImageUrl;
    }

    public EnemyViewModel setZoneImageUrl(String zoneImageUrl) {
        this.zoneImageUrl = zoneImageUrl;
        return this;
    }

    public String getZoneName() {
        return zoneName;
    }

    public EnemyViewModel setZoneName(String zoneName) {
        this.zoneName = zoneName;
        return this;
    }

    public Integer getZoneLevelRequirement() {
        return zoneLevelRequirement;
    }

    public EnemyViewModel setZoneLevelRequirement(Integer zoneLevelRequirement) {
        this.zoneLevelRequirement = zoneLevelRequirement;
        return this;
    }

    public Integer getGoldDropUpperThreshold() {
        return goldDropUpperThreshold;
    }

    public EnemyViewModel setGoldDropUpperThreshold(Integer goldDropUpperThreshold) {
        this.goldDropUpperThreshold = goldDropUpperThreshold;
        return this;
    }

    public Integer getGoldDropLowerThreshold() {
        return goldDropLowerThreshold;
    }

    public EnemyViewModel setGoldDropLowerThreshold(Integer goldDropLowerThreshold) {
        this.goldDropLowerThreshold = goldDropLowerThreshold;
        return this;
    }

    public List<ItemViewModel> getDropList() {
        return dropList;
    }

    public EnemyViewModel setDropList(List<ItemViewModel> dropList) {
        this.dropList = dropList;
        return this;
    }
}
