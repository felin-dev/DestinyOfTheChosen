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

    @Column(nullable = false)
    private Integer value;

    @ManyToOne
    private ItemEntity item;

    public StatEnum getStat() {
        return stat;
    }

    public StatEntity setStat(StatEnum stat) {
        this.stat = stat;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public StatEntity setValue(Integer value) {
        this.value = value;
        return this;
    }

    public ItemEntity getItem() {
        return item;
    }

    public StatEntity setItem(ItemEntity item) {
        this.item = item;
        return this;
    }
}
