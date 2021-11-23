package game.destinyofthechosen.model.enumeration;

public enum ItemTypeEnum {

    SWORD("Sword"), BOW("Bow"), STAFF("Staff");

    private final String itemType;

    ItemTypeEnum(String itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return itemType;
    }
}
