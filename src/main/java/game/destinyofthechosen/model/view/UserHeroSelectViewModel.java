package game.destinyofthechosen.model.view;

import java.util.ArrayList;
import java.util.List;

public class UserHeroSelectViewModel {

    private HeroSelectViewModel currentHero;
    private List<HeroSelectViewModel> heroes = new ArrayList<>();

    public HeroSelectViewModel getCurrentHero() {
        return currentHero;
    }

    public UserHeroSelectViewModel setCurrentHero(HeroSelectViewModel currentHero) {
        this.currentHero = currentHero;
        return this;
    }

    public List<HeroSelectViewModel> getHeroes() {
        return heroes;
    }

    public UserHeroSelectViewModel setHeroes(List<HeroSelectViewModel> heroes) {
        this.heroes = heroes;
        return this;
    }
}
