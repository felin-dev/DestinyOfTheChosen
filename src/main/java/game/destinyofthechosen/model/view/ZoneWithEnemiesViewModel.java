package game.destinyofthechosen.model.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZoneWithEnemiesViewModel {

    private UUID id;
    private String name;
    private String imageUrl;
    private Integer levelRequirement;
    private List<EnemyViewModel> enemies = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public ZoneWithEnemiesViewModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ZoneWithEnemiesViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ZoneWithEnemiesViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ZoneWithEnemiesViewModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

    public List<EnemyViewModel> getEnemies() {
        return enemies;
    }

    public ZoneWithEnemiesViewModel setEnemies(List<EnemyViewModel> enemies) {
        this.enemies = enemies;
        return this;
    }
}
