package game.destinyofthechosen.model.service;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

public class HeroCreationServiceModel {

    private String name;
    private HeroRoleEnum heroRole;

    public String getName() {
        return name;
    }

    public HeroCreationServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public HeroCreationServiceModel setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }
}
