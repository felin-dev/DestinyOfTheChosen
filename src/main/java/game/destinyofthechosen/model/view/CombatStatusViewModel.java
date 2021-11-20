package game.destinyofthechosen.model.view;

public class CombatStatusViewModel {

    private HeroCombatViewModel hero;
    private EnemyViewModel enemy;

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
}
