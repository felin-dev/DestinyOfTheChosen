package game.destinyofthechosen.model.enumeration;

public enum HeroRoleEnum {

    HUNTER("Hunter"), WARRIOR("Warrior"), MAGE("Mage");

    private final String heroRole;

    HeroRoleEnum(String heroRole) {
        this.heroRole = heroRole;
    }

    public String getHeroRole() {
        return heroRole;
    }
}
