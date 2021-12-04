package game.destinyofthechosen.model.session;

import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.view.ItemViewModel;
import game.destinyofthechosen.model.view.SkillViewModel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.UUID;

@Component
@SessionScope
public class CurrentHero {

    private UUID id;
    private String name;
    private HeroRoleEnum heroRole;
    private String imageUrl;
    private Integer level;
    private Integer statPoints;
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
    private ItemViewModel equippedWeapon;
    private Boolean isAlive = true;
    private List<SkillViewModel> skillList;
    private List<ItemViewModel> items;


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

    public Integer getStatPoints() {
        return statPoints;
    }

    public CurrentHero setStatPoints(Integer statPoints) {
        this.statPoints = statPoints;
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

    public CurrentHero addHealth(Integer health) {
        this.baseHealth += health;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public CurrentHero setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public CurrentHero addMana(Integer mana) {
        this.baseMana += mana;
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

    public CurrentHero addAttack(Integer attack) {
        this.baseAttack += attack;
        return this;
    }

    public Integer getBaseMagicPower() {
        return baseMagicPower;
    }

    public CurrentHero setBaseMagicPower(Integer baseMagicPower) {
        this.baseMagicPower = baseMagicPower;
        return this;
    }

    public CurrentHero addMagicPower(Integer magicPower) {
        this.baseMagicPower += magicPower;
        return this;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public CurrentHero setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }


    public CurrentHero addDefense(Integer defense) {
        this.baseDefense += defense;
        return this;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public CurrentHero setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
        return this;
    }

    public CurrentHero addStrength(Integer strength) {
        this.baseStrength += strength;
        return this;
    }

    public Integer getBaseDexterity() {
        return baseDexterity;
    }

    public CurrentHero setBaseDexterity(Integer baseDexterity) {
        this.baseDexterity = baseDexterity;
        return this;
    }

    public CurrentHero addDexterity(Integer dexterity) {
        this.baseDexterity += dexterity;
        return this;
    }

    public Integer getBaseEnergy() {
        return baseEnergy;
    }

    public CurrentHero setBaseEnergy(Integer baseEnergy) {
        this.baseEnergy = baseEnergy;
        return this;
    }

    public CurrentHero addEnergy(Integer energy) {
        this.baseEnergy += energy;
        return this;
    }

    public Integer getBaseVitality() {
        return baseVitality;
    }

    public CurrentHero setBaseVitality(Integer baseVitality) {
        this.baseVitality = baseVitality;
        return this;
    }

    public CurrentHero addVitality(Integer vitality) {
        this.baseVitality += vitality;
        return this;
    }

    public ItemViewModel getEquippedWeapon() {
        return equippedWeapon;
    }

    public CurrentHero setEquippedWeapon(ItemViewModel equippedWeapon) {
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

    public List<SkillViewModel> getSkillList() {
        return skillList;
    }

    public CurrentHero setSkillList(List<SkillViewModel> skillList) {
        this.skillList = skillList;
        return this;
    }

    public List<ItemViewModel> getItems() {
        return items;
    }

    public CurrentHero setItems(List<ItemViewModel> items) {
        this.items = items;
        return this;
    }

    public CurrentHero addItem(ItemViewModel item) {
        items.add(item);
        return this;
    }

    @Override
    public String toString() {
        return "CurrentHero{" +
                "name='" + name + '\'' +
                ", heroRole=" + heroRole +
                ", imageUrl='" + imageUrl + '\'' +
                ", level=" + level +
                ", stats=" + statPoints +
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
