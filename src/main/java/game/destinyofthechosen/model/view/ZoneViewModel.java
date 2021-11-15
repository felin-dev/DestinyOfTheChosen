package game.destinyofthechosen.model.view;

public class ZoneViewModel {

    private String name;
    private String imageUrl;
    private Integer levelRequirement;

    public String getName() {
        return name;
    }

    public ZoneViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ZoneViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ZoneViewModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }
}
