package game.destinyofthechosen.validator;

import game.destinyofthechosen.service.EnemyService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEnemyNameValidator implements ConstraintValidator<UniqueEnemyName, String> {

    private final EnemyService enemyService;

    public UniqueEnemyNameValidator(EnemyService enemyService) {
        this.enemyService = enemyService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) return false;

        return enemyService.isTheNameFree(name);
    }
}