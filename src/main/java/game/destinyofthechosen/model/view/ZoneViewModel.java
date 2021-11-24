package game.destinyofthechosen.model.view;

import java.util.UUID;

public class ZoneViewModel {

    private UUID id;
    private String zoneName;
    private String imageUrl;
    private Integer levelRequirement;

    public UUID getId() {
        return id;
    }

    public ZoneViewModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getZoneName() {
        return zoneName;
    }

    public ZoneViewModel setZoneName(String zoneName) {
        this.zoneName = zoneName;
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
