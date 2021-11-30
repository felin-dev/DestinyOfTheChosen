package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "heroes")
public class HeroEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 16)
    private String heroName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeroRoleEnum heroRole;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(nullable = false)
    private Integer statPoints = 0;

    @Column(nullable = false)
    private Integer experience = 0;

    @Column(nullable = false)
    private Integer baseHealth = 0;

    @Column(nullable = false)
    private Integer baseMana = 0;

    @Column(nullable = false)
    private Integer baseAttack = 0;

    @Column(nullable = false)
    private Integer baseMagicPower = 0;

    @Column(nullable = false)
    private Integer baseDefense = 0;

    @Column(nullable = false)
    private Integer baseVitality = 0;

    @Column(nullable = false)
    private Integer baseStrength = 0;

    @Column(nullable = false)
    private Integer baseDexterity = 0;

    @Column(nullable = false)
    private Integer baseEnergy = 0;

    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID equippedWeapon;

    @ManyToMany
    @JoinTable(name = "heroes_items",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_item_id")
    )
    private List<ItemEntity> items = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "heroes_skills",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_skill_id")
    )
    private List<SkillEntity> skills = new ArrayList<>();

    @ManyToOne
    private UserEntity user;

    public String getHeroName() {
        return heroName;
    }

    public HeroEntity setHeroName(String heroName) {
        this.heroName = heroName;
        return this;
    }

    public HeroEntity() {
    }

    public HeroEntity(String heroName, HeroRoleEnum heroRole) {
        this.heroName = heroName;
        this.heroRole = heroRole;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public HeroEntity setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public HeroEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getStatPoints() {
        return statPoints;
    }

    public HeroEntity setStatPoints(Integer statPoints) {
        this.statPoints = statPoints;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public HeroEntity setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getBaseHealth() {
        return baseHealth;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public HeroEntity setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public Integer getBaseVitality() {
        return baseVitality;
    }

    public HeroEntity setBaseVitality(Integer baseVitality) {
        baseHealth = baseVitality * 20;
        this.baseVitality = baseVitality;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public HeroEntity setBaseStrength(Integer baseStrength) {
        switch (heroRole) {
            case WARRIOR -> {
                baseAttack += baseStrength * 2;
                this.baseStrength = baseStrength;
            }
            case HUNTER, MAGE -> {
                baseAttack += baseStrength;
                this.baseStrength = baseStrength;
            }
        }

        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public HeroEntity setBaseDexterity(Integer baseDexterity) {
        switch (heroRole) {
            case WARRIOR, MAGE -> {
                baseAttack += baseDexterity;
                this.baseDexterity = baseDexterity;
            }
            case HUNTER -> {
                baseAttack += baseDexterity * 2;
                this.baseDexterity = baseDexterity;
            }
        }

        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public HeroEntity setBaseEnergy(Integer baseEnergy) {
        switch (heroRole) {
            case WARRIOR, HUNTER -> {
                baseMana = baseEnergy * 20;
                baseMagicPower += baseEnergy;
                this.baseEnergy = baseEnergy;
            }
            case MAGE -> {
                baseMana = baseEnergy * 20;
                baseMagicPower += baseEnergy * 2;
                this.baseEnergy = baseEnergy;
            }
        }
        return this;
    }

    public UUID getEquippedWeapon() {
        return equippedWeapon;
    }

    public HeroEntity setEquippedWeapon(UUID equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
        return this;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public HeroEntity setItems(List<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public HeroEntity addItem(ItemEntity item) {
        items.add(item);
        return this;
    }

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public HeroEntity setSkills(List<SkillEntity> skills) {
        this.skills = skills;
        return this;
    }

    public HeroEntity addSkill(SkillEntity skill) {
        skills.add(skill);
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public HeroEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public void levelUp() {
        level += 1;
        statPoints += 8;
        switch (heroRole) {
            case WARRIOR -> {
                setBaseDefense(baseDefense + 2);
                setBaseStrength(baseStrength + 2);
                setBaseDexterity(baseDexterity + 1);
                setBaseEnergy(baseEnergy + 1);
                setBaseVitality(baseVitality + 2);
            }
            case HUNTER -> {
                setBaseDefense(baseDefense + 1);
                setBaseStrength(baseStrength + 2);
                setBaseDexterity(baseDexterity + 3);
                setBaseEnergy(baseEnergy + 1);
                setBaseVitality(baseVitality + 1);
            }
            case MAGE -> {
                setBaseDefense(baseDefense + 1);
                setBaseStrength(baseStrength + 1);
                setBaseDexterity(baseDexterity + 1);
                setBaseEnergy(baseEnergy + 4);
                setBaseVitality(baseVitality + 1);
            }
        }
    }
}
