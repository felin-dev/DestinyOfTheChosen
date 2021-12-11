package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.model.validator.ExistingUsername;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class UsernameBindingModel {

    @ExistingUsername
    @NotBlank(message = "Username is required and must not be blank.")
    @Length(min = 5, max = 16, message = "Username should be between 5 and 16 characters.")
    private String username;

    public String getUsername() {
        return username;
    }

    public UsernameBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
