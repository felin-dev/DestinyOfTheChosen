package game.destinyofthechosen.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HasEnoughStatPointsValidator.class)
public @interface HasEnoughStatPoints {

    String message() default "Not enough stat points.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
