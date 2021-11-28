package game.destinyofthechosen.model.view;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.enumeration.SkillTypeEnum;

public class SkillViewModel {
    private String skillName;
    private String imageUrl;
    private HeroRoleEnum role;
    private SkillTypeEnum type;
    private Integer skillValue;
    private Integer level;
    private Integer manaRequired;
    private Integer coolDown;
    private Integer currentCoolDown;
    private String description;

    public String getSkillName() {
        return skillName;
    }

    public SkillViewModel setSkillName(String skillName) {
        this.skillName = skillName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SkillViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public HeroRoleEnum getRole() {
        return role;
    }

    public SkillViewModel setRole(HeroRoleEnum role) {
        this.role = role;
        return this;
    }

    public SkillTypeEnum getType() {
        return type;
    }

    public SkillViewModel setType(SkillTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getSkillValue() {
        return skillValue;
    }

    public SkillViewModel setSkillValue(Integer skillValue) {
        this.skillValue = skillValue;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public SkillViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getManaRequired() {
        return manaRequired;
    }

    public SkillViewModel setManaRequired(Integer manaRequired) {
        this.manaRequired = manaRequired;
        return this;
    }

    public Integer getCoolDown() {
        return coolDown;
    }

    public SkillViewModel setCoolDown(Integer coolDown) {
        this.coolDown = coolDown;
        return this;
    }

    public Integer getCurrentCoolDown() {
        return currentCoolDown;
    }

    public SkillViewModel setCurrentCoolDown(Integer currentCoolDown) {
        this.currentCoolDown = currentCoolDown;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SkillViewModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
