package game.destinyofthechosen.model.validator;

import game.destinyofthechosen.service.HeroService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueHeroNameValidator  implements ConstraintValidator<UniqueHeroName, String> {

    private final HeroService heroService;

    public UniqueHeroNameValidator(HeroService heroService) {
        this.heroService = heroService;
    }


    @Override
    public boolean isValid(String heroName, ConstraintValidatorContext context) {
        if (heroName == null) return false;

        return heroService.isHeroNameFree(heroName);
    }
}