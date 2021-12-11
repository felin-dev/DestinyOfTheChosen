package game.destinyofthechosen.model.view;

public class UserShopViewModel {

    private Integer gold;
    private Integer currentHeroLevel;

    public Integer getGold() {
        return gold;
    }

    public UserShopViewModel setGold(Integer gold) {
        this.gold = gold;
        return this;
    }

    public Integer getCurrentHeroLevel() {
        return currentHeroLevel;
    }

    public UserShopViewModel setCurrentHeroLevel(Integer currentHeroLevel) {
        this.currentHeroLevel = currentHeroLevel;
        return this;
    }

    public Integer getGoldCostPerChest() {
        return currentHeroLevel * 30;
    }

    public String boxImage() {
        return "https://res.cloudinary.com/felin/image/upload/v1639234940/DestinyOfTheChosen/Misc/chest.png";
    }
}
