package game.destinyofthechosen.model.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Component
@SessionScope
public class CurrentEnemy {
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
    // TODO   private List<DropListEntity> dropList;
    private String zoneImageUrl;

    public UUID getId() {
        return id;
    }

    public CurrentEnemy setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CurrentEnemy setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CurrentEnemy setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public CurrentEnemy setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public CurrentEnemy setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getBaseHealth() {
        return health;
    }

    public CurrentEnemy setHealth(Integer health) {
        this.health = health;
        return this;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public CurrentEnemy setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public Integer getAttack() {
        return attack;
    }

    public CurrentEnemy setAttack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public Integer getGoldDropUpperThreshold() {
        return goldDropUpperThreshold;
    }

    public CurrentEnemy setGoldDropUpperThreshold(Integer goldDropUpperThreshold) {
        this.goldDropUpperThreshold = goldDropUpperThreshold;
        return this;
    }

    public Integer getGoldDropLowerThreshold() {
        return goldDropLowerThreshold;
    }

    public CurrentEnemy setGoldDropLowerThreshold(Integer goldDropLowerThreshold) {
        this.goldDropLowerThreshold = goldDropLowerThreshold;
        return this;
    }

    public String getZoneImageUrl() {
        return zoneImageUrl;
    }

    public CurrentEnemy setZoneImageUrl(String zoneImageUrl) {
        this.zoneImageUrl = zoneImageUrl;
        return this;
    }
}
