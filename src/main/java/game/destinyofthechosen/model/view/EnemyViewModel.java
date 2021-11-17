package game.destinyofthechosen.model.view;

import java.util.UUID;

public class EnemyViewModel {

    private UUID id;
    private String name;
    private String imageUrl;
    private Integer level;
    private Integer health;
    private Integer Attack;

    public UUID getId() {
        return id;
    }

    public EnemyViewModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EnemyViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EnemyViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public EnemyViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public EnemyViewModel setHealth(Integer health) {
        this.health = health;
        return this;
    }

    public Integer getAttack() {
        return Attack;
    }

    public EnemyViewModel setAttack(Integer attack) {
        Attack = attack;
        return this;
    }
}
