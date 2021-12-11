package game.destinyofthechosen.model.service;

import game.destinyofthechosen.model.binding.UsernameBindingModel;

public class UsernameServiceModel {
    private String username;

    public String getUsername() {
        return username;
    }

    public UsernameServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
