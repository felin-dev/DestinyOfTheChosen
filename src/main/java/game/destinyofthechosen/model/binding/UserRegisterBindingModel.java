package game.destinyofthechosen.model.binding;

import game.destinyofthechosen.validator.UniqueEmail;
import game.destinyofthechosen.validator.UniqueUsername;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegisterBindingModel {

    @UniqueUsername
    @NotBlank(message = "Username is required and must not be blank.")
    @Length(min = 5, max = 35, message = "Username should be between 5 and 35 characters.")
    private String username;

    @NotBlank(message = "Password is required and must not be blank.")
    @Length(min = 8, max = 40, message = "Password should be between 8 and 40 characters.")
    private String rawPassword;

    @NotBlank(message = "Confirm password is required and must not be blank.")
    @Length(min = 8, max = 40, message = "Password should be between 8 and 40 characters.")
    private String confirmPassword;

    @UniqueEmail
    @NotBlank(message = "Email is required and must not be blank.")
    @Email(message = "Please enter a valid email address.")
    @Length(max = 320, message = "Maximum email length is 320 characters.")
    private String email;

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public UserRegisterBindingModel setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
