package game.destinyofthechosen.web;

import game.destinyofthechosen.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Principal principal) {

        if (userService.userHasNoSelectedHero(principal.getName())) {
            return "redirect:/heroes/select";
        }

        return "home";
    }
}
