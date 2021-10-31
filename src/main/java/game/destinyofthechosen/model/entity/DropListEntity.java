package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.ItemNameEnum;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "dropList")
public class DropListEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemNameEnum ItemName;

    @ManyToOne(targetEntity = EnemyEntity.class)
    private UUID enemy;

    public ItemNameEnum getItemName() {
        return ItemName;
    }

    public DropListEntity setItemName(ItemNameEnum itemName) {
        ItemName = itemName;
        return this;
    }

    public UUID getEnemy() {
        return enemy;
    }

    public DropListEntity setEnemy(UUID enemy) {
        this.enemy = enemy;
        return this;
    }
}
