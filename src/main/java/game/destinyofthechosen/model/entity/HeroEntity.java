package game.destinyofthechosen.model.entity;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "heroes")
public class HeroEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 35)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeroRoleEnum heroRole;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer experience;

    @Column(nullable = false)
    private Integer baseHealth;

    @Column(nullable = false)
    private Integer baseAttack;

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

    private UUID equippedWeapon;

    @OneToMany(mappedBy = "hero")
    private Collection<ItemEntity> items;

    @ManyToOne(targetEntity = UserEntity.class)
    private UUID user;

    public String getName() {
        return name;
    }

    public HeroEntity setName(String name) {
        this.name = name;
        return this;
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

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public HeroEntity setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
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

    public Collection<ItemEntity> getItems() {
        return items;
    }

    public HeroEntity setItems(Collection<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public UUID getUser() {
        return user;
    }

    public HeroEntity setUser(UUID user) {
        this.user = user;
        return this;
    }
}
