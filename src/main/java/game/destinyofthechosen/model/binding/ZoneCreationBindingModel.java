package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.validator.NotNullFile;
import game.destinyofthechosen.model.validator.UniqueEnemyName;
import game.destinyofthechosen.model.validator.UniqueZoneName;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ZoneCreationBindingModel {

    @UniqueZoneName
    @NotBlank(message = "Zone name is required and must not be blank.")
    @Length(min = 2, max = 32, message = "Zone name should be between 2 and 32 characters.")
    private String name;

    @NotNullFile
    private MultipartFile image;

    @NotNull
    @Positive
    private Integer levelRequirement;

    public String getName() {
        return name;
    }

    public ZoneCreationBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public ZoneCreationBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public ZoneCreationBindingModel setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }
}
