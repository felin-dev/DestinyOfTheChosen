package game.destinyofthechosen.model.view;

import java.util.ArrayList;
import java.util.List;

public class UserHeroSelectViewModel {

    private HeroInfoViewModel currentHero;
    private List<HeroInfoViewModel> heroes = new ArrayList<>();

    public HeroInfoViewModel getCurrentHero() {
        return currentHero;
    }

    public UserHeroSelectViewModel setCurrentHero(HeroInfoViewModel currentHero) {
        this.currentHero = currentHero;
        return this;
    }

    public List<HeroInfoViewModel> getHeroes() {
        return heroes;
    }

    public UserHeroSelectViewModel setHeroes(List<HeroInfoViewModel> heroes) {
        this.heroes = heroes;
        return this;
    }
}
