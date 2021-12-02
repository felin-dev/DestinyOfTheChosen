package game.destinyofthechosen.model.validator;

import game.destinyofthechosen.model.binding.StatUpBindingModel;
import game.destinyofthechosen.service.HeroService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HasEnoughStatPointsValidator implements ConstraintValidator<HasEnoughStatPoints, StatUpBindingModel> {

    private final HeroService heroService;

    public HasEnoughStatPointsValidator(HeroService heroService) {
        this.heroService = heroService;
    }

    @Override
    public boolean isValid(StatUpBindingModel statUpBindingModel, ConstraintValidatorContext context) {
        return heroService.hasEnoughStatPoints(getStats(statUpBindingModel));
    }

    private int getStats(StatUpBindingModel statUpBindingModel) {
        Integer total = 0;
        if (statUpBindingModel.getStrength() != null) total += statUpBindingModel.getStrength();
        if (statUpBindingModel.getDexterity() != null) total += statUpBindingModel.getDexterity();
        if (statUpBindingModel.getEnergy() != null) total += statUpBindingModel.getEnergy();
        if (statUpBindingModel.getVitality() != null) total += statUpBindingModel.getVitality();

        return total;
    }
}
