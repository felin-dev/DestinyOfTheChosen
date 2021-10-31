package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.StatEnum;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "stats")
public class StatEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatEnum stat;

    @ManyToOne(targetEntity = ItemEntity.class)
    private UUID item;

    public StatEnum getStat() {
        return stat;
    }

    public StatEntity setStat(StatEnum stat) {
        this.stat = stat;
        return this;
    }

    public UUID getItem() {
        return item;
    }

    public StatEntity setItem(UUID item) {
        this.item = item;
        return this;
    }
}
