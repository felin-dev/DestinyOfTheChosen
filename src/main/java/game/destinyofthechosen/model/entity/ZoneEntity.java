package game.destinyofthechosen.model.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "zones")
public class ZoneEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 32)
    private String zoneName;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer levelRequirement;

    @OneToMany(mappedBy = "zone")
    private Set<EnemyEntity> enemies = new LinkedHashSet<>();

    public String getZoneName() {
        return zoneName;
    }

    public ZoneEntity setZoneName(String zoneName) {
        this.zoneName = zoneName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ZoneEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ZoneEntity setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

    public Set<EnemyEntity> getEnemies() {
        return enemies;
    }

    public ZoneEntity setEnemies(Set<EnemyEntity> enemies) {
        this.enemies = enemies;
        return this;
    }

    public void addEnemy(EnemyEntity enemyEntity) {
        enemies.add(enemyEntity);
    }
}
