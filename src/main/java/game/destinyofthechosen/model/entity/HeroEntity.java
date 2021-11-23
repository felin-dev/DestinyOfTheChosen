package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "heroes")
public class HeroEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 16)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeroRoleEnum heroRole;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(nullable = false)
    private Integer stats = 0;

    @Column(nullable = false)
    private Integer experience = 0;

    @Column(nullable = false)
    private Integer baseHealth;

    @Column(nullable = false)
    private Integer baseMana;

    @Column(nullable = false)
    private Integer baseAttack;

    @Column(nullable = false)
    private Integer baseMagicPower;

    @Column(nullable = false)
    private Integer baseDefense;

    @Column(nullable = false)
    private Integer baseVitality;

    @Column(nullable = false)
    private Integer baseStrength;

    @Column(nullable = false)
    private Integer baseDexterity;

    @Column(nullable = false)
    private Integer baseEnergy;

    @Type(type = "uuid-char")
    private UUID equippedWeapon;

    @OneToMany(mappedBy = "hero")
    private List<ItemEntity> items = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "heroes_skills",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_skill_id")
    )
    private List<SkillEntity> skills = new ArrayList<>();

    @ManyToOne
    private UserEntity user;

    public String getName() {
        return name;
    }

    public HeroEntity setName(String name) {
        this.name = name;
        return this;
    }

    public HeroEntity() {
    }

    public HeroEntity(String name, HeroRoleEnum heroRole) {
        this.name = name;
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

    public HeroEntity setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getStats() {
        return stats;
    }

    public HeroEntity setStats(Integer stats) {
        this.stats = stats;
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

    public HeroEntity setBaseHealth(Integer baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public HeroEntity setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public HeroEntity setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public HeroEntity setBaseMagicPower(Integer baseMagicPower) {
        this.baseMagicPower = baseMagicPower;
        return this;
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
        this.baseVitality = baseVitality;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public HeroEntity setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public HeroEntity setBaseDexterity(Integer baseDexterity) {
        this.baseDexterity = baseDexterity;
        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public HeroEntity setBaseEnergy(Integer baseEnergy) {
        this.baseEnergy = baseEnergy;
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

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public HeroEntity setSkills(List<SkillEntity> skills) {
        this.skills = skills;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public HeroEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public HeroEntity levelUp() {
        level += 1;
        stats += 8;
        switch (heroRole) {
            case WARRIOR -> {
//                setBaseHealth(180)     // vitality x20
//                setBaseMana(100)       // energy x20
//                setBaseAttack(17)      // strength x2 + dexterity x1
//                setBaseMagicPower(5)   // energy x1
//                setBaseDefense(12)     // base defense increases with items
//                setBaseDexterity(5)
//                setBaseStrength(6)
//                setBaseEnergy(5)
                baseHealth += 40;
                baseMana += 20;
                baseAttack += 5;
                baseMagicPower += 1;
                baseDefense += 2;
                baseStrength += 2;
                baseDexterity += 1;
                baseEnergy += 1;
                baseVitality += 2;
            }
            case HUNTER ->  {
                //.setBaseHealth(120)     // vitality x20
                //.setBaseMana(80)        // energy x20
                //.setBaseAttack(25)      // dexterity x2 + strength x1
                //.setBaseMagicPower(4)   // energy x1
                //.setBaseDefense(10)     // base defense increases with items
                //.setBaseDexterity(10)
                //.setBaseStrength(5)
                //.setBaseEnergy(4)
                //.setBaseVitality(6)
                baseHealth += 20;
                baseMana += 20;
                baseAttack += 8;
                baseMagicPower += 2;
                baseDefense += 1;
                baseStrength += 2;
                baseDexterity += 3;
                baseEnergy += 1;
                baseVitality += 1;
            }
            case MAGE -> {
                //.setBaseHealth(100)     // vitality x20
                //.setBaseMana(240)       // energy x20
                //.setBaseAttack(8)       // dexterity x1 + strength x1
                //.setBaseMagicPower(24)  // energy x2
                //.setBaseDefense(8)      // base defense increases with items
                //.setBaseDexterity(4)
                //.setBaseStrength(4)
                //.setBaseEnergy(12)
                //.setBaseVitality(5)
                baseHealth += 20;
                baseMana += 80;
                baseAttack += 2;
                baseMagicPower += 8;
                baseDefense += 1;
                baseStrength += 1;
                baseDexterity += 1;
                baseEnergy += 4;
                baseVitality += 1;
            }
        }
        return this;
    }
}
