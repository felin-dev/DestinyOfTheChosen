package game.destinyofthechosen.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingUsernameValidator.class)
public @interface ExistingUsername {

    String message() default "User with such username does not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
