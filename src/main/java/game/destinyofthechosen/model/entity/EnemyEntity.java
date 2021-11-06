package game.destinyofthechosen.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enemies")
public class EnemyEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 35)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer attack;

    @Column(nullable = false)
    private Integer goldDropUpperThreshold;

    @Column(nullable = false)
    private Integer goldDropLowerThreshold;

    @OneToMany(mappedBy = "enemy")
    private List<DropListEntity> dropList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public EnemyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EnemyEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public EnemyEntity setHealth(Integer health) {
        this.health = health;
        return this;
    }

    public Integer getAttack() {
        return attack;
    }

    public EnemyEntity setAttack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public Integer getGoldDropUpperThreshold() {
        return goldDropUpperThreshold;
    }

    public EnemyEntity setGoldDropUpperThreshold(Integer goldDropUpperThreshold) {
        this.goldDropUpperThreshold = goldDropUpperThreshold;
        return this;
    }

    public Integer getGoldDropLowerThreshold() {
        return goldDropLowerThreshold;
    }

    public EnemyEntity setGoldDropLowerThreshold(Integer goldDropLowerThreshold) {
        this.goldDropLowerThreshold = goldDropLowerThreshold;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public EnemyEntity setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public List<DropListEntity> getDropList() {
        return dropList;
    }

    public EnemyEntity setDropList(List<DropListEntity> dropList) {
        this.dropList = dropList;
        return this;
    }

    @Override
    public String toString() {
        return "EnemyEntity{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", attack=" + attack +
                ", goldDropUpperThreshold=" + goldDropUpperThreshold +
                ", goldDropLowerThreshold=" + goldDropLowerThreshold +
                ", dropList=" + dropList +
                '}';
    }
}
