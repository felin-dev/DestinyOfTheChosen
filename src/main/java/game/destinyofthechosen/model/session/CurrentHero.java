package game.destinyofthechosen.model.session;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Component
@SessionScope
public class CurrentHero {

    private UUID id;
    private String name;
    private HeroRoleEnum heroRole;
    private String imageUrl;
    private Integer level;
    private Integer stats;
    private Integer experience;
    private Integer baseHealth;
    private Integer baseMana;
    private Integer currentHealth;
    private Integer currentMana;
    private Integer baseAttack;
    private Integer baseMagicPower;
    private Integer baseDefense;
    private Integer baseVitality;
    private Integer baseStrength;
    private Integer baseDexterity;
    private Integer baseEnergy;
    private UUID equippedWeapon;
    private Boolean isAlive = true;
    // TODO   private List<ItemEntity> items;


    public UUID getId() {
        return id;
    }

    public CurrentHero setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CurrentHero setName(String name) {
        this.name = name;
        return this;
    }

    public HeroRoleEnum getHeroRole() {
        return heroRole;
    }

    public CurrentHero setHeroRole(HeroRoleEnum heroRole) {
        this.heroRole = heroRole;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CurrentHero setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public CurrentHero setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getStats() {
        return stats;
    }

    public CurrentHero setStats(Integer stats) {
        this.stats = stats;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public CurrentHero setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public Integer getBaseHealth() {
        return baseHealth;
    }

    public CurrentHero setBaseHealth(Integer baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public CurrentHero setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public CurrentHero setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public Integer getCurrentMana() {
        return currentMana;
    }

    public CurrentHero setCurrentMana(Integer currentMana) {
        this.currentMana = currentMana;
        return this;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public CurrentHero setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public CurrentHero setBaseMagicPower(Integer baseMagicPower) {
        this.baseMagicPower = baseMagicPower;
        return this;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public CurrentHero setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public Integer getBaseVitality() {
        return baseVitality;
    }

    public CurrentHero setBaseVitality(Integer baseVitality) {
        this.baseVitality = baseVitality;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public CurrentHero setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public CurrentHero setBaseDexterity(Integer baseDexterity) {
        this.baseDexterity = baseDexterity;
        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public CurrentHero setBaseEnergy(Integer baseEnergy) {
        this.baseEnergy = baseEnergy;
        return this;
    }

    public UUID getEquippedWeapon() {
        return equippedWeapon;
    }

    public CurrentHero setEquippedWeapon(UUID equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
        return this;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public CurrentHero setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
        return this;
    }

    @Override
    public String toString() {
        return "CurrentHero{" +
                "name='" + name + '\'' +
                ", heroRole=" + heroRole +
                ", imageUrl='" + imageUrl + '\'' +
                ", level=" + level +
                ", stats=" + stats +
                ", experience=" + experience +
                ", baseHealth=" + baseHealth +
                ", baseMana=" + baseMana +
                ", currentHealth=" + currentHealth +
                ", currentMana=" + currentMana +
                ", baseAttack=" + baseAttack +
                ", baseMagicPower=" + baseMagicPower +
                ", baseDefense=" + baseDefense +
                ", baseVitality=" + baseVitality +
                ", baseStrength=" + baseStrength +
                ", baseDexterity=" + baseDexterity +
                ", baseEnergy=" + baseEnergy +
                ", equippedWeapon=" + equippedWeapon +
                '}';
    }
}
