package game.destinyofthechosen.model.view;

import java.util.UUID;

public class HeroCombatViewModel {

    private String name;
    private String imageUrl;
    private Integer level;
    private Integer baseHealth;
    private Integer currentHealth;
    private Integer baseMana;
    private Integer currentMana;
    private UUID equippedWeapon;

    public String getName() {
        return name;
    }

    public HeroCombatViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public HeroCombatViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public HeroCombatViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getBaseHealth() {
        return baseHealth;
    }

    public HeroCombatViewModel setBaseHealth(Integer baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public HeroCombatViewModel setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public Integer getBaseMana() {
        return baseMana;
    }

    public HeroCombatViewModel setBaseMana(Integer baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public Integer getCurrentMana() {
        return currentMana;
    }

    public HeroCombatViewModel setCurrentMana(Integer currentMana) {
        this.currentMana = currentMana;
        return this;
    }

    public UUID getEquippedWeapon() {
        return equippedWeapon;
    }

    public HeroCombatViewModel setEquippedWeapon(UUID equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
        return this;
    }
}
