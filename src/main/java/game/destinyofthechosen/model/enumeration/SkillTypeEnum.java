package game.destinyofthechosen.model.enumeration;

public enum SkillTypeEnum {

    DAMAGE("Damage"), DEFENSE_BUFF("Defense Buff"), HEAL("Heal"), IMMOBILIZE("Immobilize");

    private final String skillType;

    SkillTypeEnum(String skillType) {
        this.skillType = skillType;
    }

    public String getType() {
        return skillType;
    }
}
