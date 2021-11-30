package game.destinyofthechosen.model.view;

public class CombatStatusViewModel {

    private HeroCombatViewModel hero;
    private EnemyViewModel enemy;
    private String itemDrop;
    private Integer moneyDrop;
    private Integer leveledUp;

    public HeroCombatViewModel getHero() {
        return hero;
    }

    public CombatStatusViewModel setHero(HeroCombatViewModel hero) {
        this.hero = hero;
        return this;
    }

    public EnemyViewModel getEnemy() {
        return enemy;
    }

    public CombatStatusViewModel setEnemy(EnemyViewModel enemy) {
        this.enemy = enemy;
        return this;
    }

    public String getItemDrop() {
        return itemDrop;
    }

    public CombatStatusViewModel setItemDrop(String itemDrop) {
        this.itemDrop = itemDrop;
        return this;
    }

    public Integer getMoneyDrop() {
        return moneyDrop;
    }

    public CombatStatusViewModel setMoneyDrop(Integer moneyDrop) {
        this.moneyDrop = moneyDrop;
        return this;
    }

    public Integer getLeveledUp() {
        return leveledUp;
    }

    public CombatStatusViewModel setLeveledUp(Integer leveledUp) {
        this.leveledUp = leveledUp;
        return this;
    }
}
