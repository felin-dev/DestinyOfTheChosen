package game.destinyofthechosen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // TODO
    @GetMapping("/users/profile")
    public String userProfile() {
        return "profile";
    }

    // TODO
    @GetMapping("/users/inventory")
    public String userInventory() {
        return "inventory";
    }
}
