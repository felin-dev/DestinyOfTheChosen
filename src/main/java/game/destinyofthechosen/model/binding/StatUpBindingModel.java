package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.validator.HasEnoughStatPoints;

import javax.validation.constraints.PositiveOrZero;

@HasEnoughStatPoints
public class StatUpBindingModel {

    @PositiveOrZero
    private Integer strength;

    @PositiveOrZero
    private Integer dexterity;

    @PositiveOrZero
    private Integer energy;

    @PositiveOrZero
    private Integer vitality;

    public Integer getStrength() {
        return strength;
    }

    public StatUpBindingModel setStrength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public StatUpBindingModel setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public Integer getEnergy() {
        return energy;
    }

    public StatUpBindingModel setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public Integer getVitality() {
        return vitality;
    }

    public StatUpBindingModel setVitality(Integer vitality) {
        this.vitality = vitality;
        return this;
    }

    @Override
    public String toString() {
        return "StatUpBindingModel{" +
                "strength=" + strength +
                ", dexterity=" + dexterity +
                ", energy=" + energy +
                ", vitality=" + vitality +
                '}';
    }
}
