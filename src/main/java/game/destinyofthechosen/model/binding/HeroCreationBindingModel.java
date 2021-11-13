package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.validator.UniqueHeroName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HeroCreationBindingModel {

    @UniqueHeroName
    @NotBlank(message = "Hero name is required and must not be blank.")
    @Length(min = 2, max = 16, message = "Hero name should be between 2 and 16 characters.")
    private String name;

    @NotNull(message = "Please select a hero class.")
    private HeroRoleEnum heroRole;

    public String getName() {
        return name;
    }

    public HeroCreationBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public HeroCreationBindingModel setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }
}
