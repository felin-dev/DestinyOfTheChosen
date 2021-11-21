package game.destinyofthechosen.model.view;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

public class HeroSelectedViewModel {

    private String name;
    private HeroRoleEnum heroRole;
    private Integer level;
    private Integer experience;

    public String getName() {
        return name;
    }

    public HeroSelectedViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public HeroSelectedViewModel setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public HeroSelectedViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public HeroSelectedViewModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }
}
