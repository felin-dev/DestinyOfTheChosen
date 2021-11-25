package game.destinyofthechosen.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "drop_list")
public class DropListEntity extends BaseEntity {

    @Column(nullable = false)
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID itemId;

    @ManyToOne
    private EnemyEntity enemy;

    public UUID getItemName() {
        return itemId;
    }

    public DropListEntity setItemName(UUID itemId) {
        this.itemId = itemId;
        return this;
    }

    public EnemyEntity getEnemy() {
        return enemy;
    }

    public DropListEntity setEnemy(EnemyEntity enemy) {
        this.enemy = enemy;
        return this;
    }
}
