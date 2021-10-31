package game.destinyofthechosen.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "enemies")
public class EnemyEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 35)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer attack;

    private Integer goldDropUpperLimit;

    private Integer goldDropLowerLimit;

    @OneToMany(mappedBy = "enemy")
    private List<DropListEntity> dropList;

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

    public Integer getGoldDropUpperLimit() {
        return goldDropUpperLimit;
    }

    public EnemyEntity setGoldDropUpperLimit(Integer goldDropUpperLimit) {
        this.goldDropUpperLimit = goldDropUpperLimit;
        return this;
    }

    public Integer getGoldDropLowerLimit() {
        return goldDropLowerLimit;
    }

    public EnemyEntity setGoldDropLowerLimit(Integer goldDropLowerLimit) {
        this.goldDropLowerLimit = goldDropLowerLimit;
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
}
