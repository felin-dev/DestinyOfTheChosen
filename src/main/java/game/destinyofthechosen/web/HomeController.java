package game.destinyofthechosen.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home() {
        // TODO
        return "redirect:/heroes/create";
//        return "home";
    }
}
