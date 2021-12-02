package game.destinyofthechosen.model.service;

public class StatUpServiceModel {

    private Integer strength;
    private Integer dexterity;
    private Integer energy;
    private Integer vitality;

    public Integer getStrength() {
        return strength;
    }

    public StatUpServiceModel setStrength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public StatUpServiceModel setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public Integer getEnergy() {
        return energy;
    }

    public StatUpServiceModel setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public Integer getVitality() {
        return vitality;
    }

    public StatUpServiceModel setVitality(Integer vitality) {
        this.vitality = vitality;
        return this;
    }
}
