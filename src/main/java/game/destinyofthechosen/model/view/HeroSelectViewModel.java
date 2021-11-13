package game.destinyofthechosen.model.view;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

import java.util.UUID;

public class HeroSelectViewModel {

    private UUID heroId;
    private String name;
    private HeroRoleEnum heroRole;
    private String imageUrl;
    private Integer level;
    private Integer experience;
    private Integer baseHealth;
    private Integer baseMana;
    private Integer baseAttack;
    private Integer baseMagicPower;
    private Integer baseDefense;
    private Integer baseVitality;
    private Integer baseStrength;
    private Integer baseDexterity;
    private Integer baseEnergy;

    public UUID getHeroId() {
        return heroId;
    }

    public HeroSelectViewModel setHeroId(UUID heroId) {
        this.heroId = heroId;
        return this;
    }

    public String getName() {
        return name;
    }

    public HeroSelectViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public HeroSelectViewModel setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public HeroSelectViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public HeroSelectViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public HeroSelectViewModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getBaseHealth() {
        return baseHealth;
    }

    public HeroSelectViewModel setBaseHealth(Integer baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public HeroSelectViewModel setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public HeroSelectViewModel setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public HeroSelectViewModel setBaseMagicPower(Integer baseMagicPower) {
        this.baseMagicPower = baseMagicPower;
        return this;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public HeroSelectViewModel setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public Integer getBaseVitality() {
        return baseVitality;
    }

    public HeroSelectViewModel setBaseVitality(Integer baseVitality) {
        this.baseVitality = baseVitality;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public HeroSelectViewModel setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public HeroSelectViewModel setBaseDexterity(Integer baseDexterity) {
        this.baseDexterity = baseDexterity;
        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public HeroSelectViewModel setBaseEnergy(Integer baseEnergy) {
        this.baseEnergy = baseEnergy;
        return this;
    }
}