package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.enumeration.SkillTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "skills")
public class SkillEntity extends BaseEntity {

    @Column(nullable = false, length = 16)
    private String skillName;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HeroRoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillTypeEnum type;

    @Column(nullable = false)
    private Integer skillValue;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer manaRequired;

    @Column(nullable = false, name = "cool_down")
    private Integer coolDown;

    @Column(nullable = false)
    private String description;

    public SkillEntity() {
    }

    public SkillEntity(String skillName, String imageUrl, HeroRoleEnum role, SkillTypeEnum type, Integer skillValue, Integer level, Integer manaRequired, Integer coolDown, String description) {
        this.skillName = skillName;
        this.imageUrl = imageUrl;
        this.role = role;
        this.type = type;
        this.skillValue = skillValue;
        this.level = level;
        this.manaRequired = manaRequired;
        this.coolDown = coolDown;
        this.description = description;
    }

    public String getSkillName() {
        return skillName;
    }

    public SkillEntity setSkillName(String skillName) {
        this.skillName = skillName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SkillEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public HeroRoleEnum getRole() {
        return role;
    }

    public SkillEntity setRole(HeroRoleEnum role) {
        this.role = role;
        return this;
    }

    public SkillTypeEnum getType() {
        return type;
    }

    public SkillEntity setType(SkillTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getSkillValue() {
        return skillValue;
    }

    public SkillEntity setSkillValue(Integer skillValue) {
        this.skillValue = skillValue;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public SkillEntity setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getManaRequired() {
        return manaRequired;
    }

    public SkillEntity setManaRequired(Integer manaRequired) {
        this.manaRequired = manaRequired;
        return this;
    }

    public Integer getCoolDown() {
        return coolDown;
    }

    public SkillEntity setCoolDown(Integer coolDown) {
        this.coolDown = coolDown;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SkillEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
