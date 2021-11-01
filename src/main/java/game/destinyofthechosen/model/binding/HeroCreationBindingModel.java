package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.validator.UniqueHeroName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HeroCreationBindingModel {

    @UniqueHeroName
    @NotBlank(message = "Username is required and must not be blank.")
    @Length(min = 5, max = 35, message = "Username should be between 5 and 35 characters.")
    private String name;

    @NotNull
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
