package game.destinyofthechosen.model.service;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

public class HeroCreationServiceModel {

    private String heroName;
    private HeroRoleEnum heroRole;

    public String getHeroName() {
        return heroName;
    }

    public HeroCreationServiceModel setHeroName(String heroName) {
        this.heroName = heroName;
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
