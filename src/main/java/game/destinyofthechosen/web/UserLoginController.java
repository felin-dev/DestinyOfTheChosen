package game.destinyofthechosen.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserLoginController {

    @ModelAttribute("wrongCredentials")
    public Boolean wrongCredentials() {
        return false;
    }

    @ModelAttribute("username")
    public String username() {
        return "";
    }

    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String loginError(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
            String username, RedirectAttributes redirectAttributes) {

        redirectAttributes
                .addFlashAttribute("wrongCredentials", true)
                .addFlashAttribute("username", username);

        return "redirect:/users/login";
    }
}
