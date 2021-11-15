package game.destinyofthechosen.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueZoneNameValidator.class)
public @interface UniqueZoneName {

    String message() default "Zone name is already taken.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
