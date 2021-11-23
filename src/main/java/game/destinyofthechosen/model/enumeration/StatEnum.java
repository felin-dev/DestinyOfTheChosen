package game.destinyofthechosen.model.enumeration;

public enum StatEnum {

    ATTACK("Attack"), MAGIC_POWER("Magic Power"), DEFENSE("Defense"),
    HEALTH("Health"), MANA("Mana"), VITALITY("Vitality"),
    STRENGTH("Strength"), DEXTERITY("Dexterity"), ENERGY("Energy");

    private final String statName;

    StatEnum(String statName) {
        this.statName = statName;
    }

    public String getType() {
        return statName;
    }
}
