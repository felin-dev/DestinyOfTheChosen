package game.destinyofthechosen.model.enumeration;

public enum ItemNameEnum {

    // Warrior
    BROADSWORD("Broadsword"), SWIFT_BLADE("Swift Blade"), DOOM_BLADE("Doom Blade"),

    // Hunter
    COMPOSITE_BOW("Composite Bow"), MAPLE_BOW("Maple Bow"), CROSSFIRE("Crossfire"),

    // Mage
    ASH_WOOD_SCEPTER("Ash Wood Scepter"), WAR_STAFF("War Staff"), FATE("Fate");

    private final String itemName;

    ItemNameEnum(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
