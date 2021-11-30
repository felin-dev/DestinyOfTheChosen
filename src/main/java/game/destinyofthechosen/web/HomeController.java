package game.destinyofthechosen.web;

import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;
    private final HeroService heroService;

    public HomeController(UserService userService, HeroService heroService) {
        this.userService = userService;
        this.heroService = heroService;
    }

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {

        if (userService.userHasNoSelectedHero(principal.getName())) {
            return "redirect:/heroes/select";
        }

        model.addAttribute("currentHero",
                heroService.getCurrentHero(principal.getName()));

        return "home";
    }
}
