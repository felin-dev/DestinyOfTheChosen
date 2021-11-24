package game.destinyofthechosen.model.service;

import org.springframework.web.multipart.MultipartFile;

public class ZoneCreationServiceModel {

    private String zoneName;
    private MultipartFile image;
    private Integer levelRequirement;

    public String getZoneName() {
        return zoneName;
    }

    public ZoneCreationServiceModel setZoneName(String zoneName) {
        this.zoneName = zoneName;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public ZoneCreationServiceModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ZoneCreationServiceModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }
}
