package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.enumeration.SkillTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "skills")
public class SkillEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 16)
    private String name;

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

    @Column(nullable = false, name = "cool_down")
    private Integer coolDown;

    @Column(nullable = false)
    private String description;

    public String getName() {
        return name;
    }

    public SkillEntity setName(String name) {
        this.name = name;
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
