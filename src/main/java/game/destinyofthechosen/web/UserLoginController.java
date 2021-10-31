package game.destinyofthechosen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users/login-error")
    public String loginError() {
        return "login";
    }
}
