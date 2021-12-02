package game.destinyofthechosen.model.view;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;

import java.util.List;
import java.util.UUID;

public class CurrentHeroViewModel {

    private String name;
    private HeroRoleEnum heroRole;
    private String imageUrl;
    private Integer level;
    private Integer statPoints;
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
    private ItemViewModel equippedWeapon;
    private List<SkillViewModel> skillList;
    private List<ItemViewModel> items;

    public String getName() {
        return name;
    }

    public CurrentHeroViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public CurrentHeroViewModel setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CurrentHeroViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public CurrentHeroViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getStatPoints() {
        return statPoints;
    }

    public CurrentHeroViewModel setStatPoints(Integer statPoints) {
        this.statPoints = statPoints;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public CurrentHeroViewModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getBaseHealth() {
        return baseHealth;
    }

    public CurrentHeroViewModel setBaseHealth(Integer baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public CurrentHeroViewModel setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public CurrentHeroViewModel setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public CurrentHeroViewModel setBaseMagicPower(Integer baseMagicPower) {
        this.baseMagicPower = baseMagicPower;
        return this;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public CurrentHeroViewModel setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public Integer getBaseVitality() {
        return baseVitality;
    }

    public CurrentHeroViewModel setBaseVitality(Integer baseVitality) {
        this.baseVitality = baseVitality;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public CurrentHeroViewModel setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public CurrentHeroViewModel setBaseDexterity(Integer baseDexterity) {
        this.baseDexterity = baseDexterity;
        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public CurrentHeroViewModel setBaseEnergy(Integer baseEnergy) {
        this.baseEnergy = baseEnergy;
        return this;
    }

    public ItemViewModel getEquippedWeapon() {
        return equippedWeapon;
    }

    public CurrentHeroViewModel setEquippedWeapon(ItemViewModel equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
        return this;
    }

    public List<SkillViewModel> getSkillList() {
        return skillList;
    }

    public CurrentHeroViewModel setSkillList(List<SkillViewModel> skillList) {
        this.skillList = skillList;
        return this;
    }

    public List<ItemViewModel> getItems() {
        return items;
    }

    public CurrentHeroViewModel setItems(List<ItemViewModel> items) {
        this.items = items;
        return this;
    }

    public String heroInfo() {
        return String.format("%s <br> lvl %d %s <br> Experience: %d <br> Health: %d <br> Mana: %d <br> Attack: %d <br> Magic Power: %d <br> Defense: %d <br> Vitality: %d <br> Strength: %d <br> Dexterity: %d <br> Energy: %d"
                , name, level, heroRole.getHeroRole(), experience, baseHealth, baseMana, baseAttack, baseMagicPower,
                baseDefense, baseVitality, baseStrength, baseDexterity, baseEnergy);
    }
}
