package game.destinyofthechosen.model.view;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

import java.util.UUID;

public class HeroInfoViewModel {

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

    public HeroInfoViewModel setHeroId(UUID heroId) {
        this.heroId = heroId;
        return this;
    }

    public String getName() {
        return name;
    }

    public HeroInfoViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public HeroInfoViewModel setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public HeroInfoViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public HeroInfoViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public HeroInfoViewModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getBaseHealth() {
        return baseHealth;
    }

    public HeroInfoViewModel setBaseHealth(Integer baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public HeroInfoViewModel setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public HeroInfoViewModel setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public HeroInfoViewModel setBaseMagicPower(Integer baseMagicPower) {
        this.baseMagicPower = baseMagicPower;
        return this;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public HeroInfoViewModel setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public Integer getBaseVitality() {
        return baseVitality;
    }

    public HeroInfoViewModel setBaseVitality(Integer baseVitality) {
        this.baseVitality = baseVitality;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public HeroInfoViewModel setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public HeroInfoViewModel setBaseDexterity(Integer baseDexterity) {
        this.baseDexterity = baseDexterity;
        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public HeroInfoViewModel setBaseEnergy(Integer baseEnergy) {
        this.baseEnergy = baseEnergy;
        return this;
    }

    public String heroInfo() {
        if (heroId == null) return "";
        return String.format("%s <br> lvl %d %s <br> Experience: %d <br> Health: %d <br> Mana: %d <br> Attack: %d <br> Magic Power: %d <br> Defense: %d <br> Vitality: %d <br> Strength: %d <br> Dexterity: %d <br> Energy: %d"
                , name, level, heroRole.getHeroRole(), experience, baseHealth, baseMana, baseAttack, baseMagicPower,
                baseDefense, baseVitality, baseStrength, baseDexterity, baseEnergy);
    }
}
