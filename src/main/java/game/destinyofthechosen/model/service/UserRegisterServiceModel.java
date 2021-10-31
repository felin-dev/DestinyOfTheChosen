package game.destinyofthechosen.model.service;

public class UserRegisterServiceModel {

    private String username;
    private String rawPassword;
    private String email;

    public String getUsername() {
        return username;
    }

    public UserRegisterServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public UserRegisterServiceModel setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
