package game.destinyofthechosen.model.validator;

import game.destinyofthechosen.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingUsernameValidator implements ConstraintValidator<ExistingUsername, String> {

    private final UserService userService;

    public ExistingUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) return false;

        return !userService.isUsernameFree(username);
    }
}
