package game.destinyofthechosen.web;

import game.destinyofthechosen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ShopController {

    private final UserService userService;

    public ShopController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("shopMessage")
    public String shopMessage() {
        return null;
    }

    @GetMapping("/shop/buy")
    public String openShop(Principal principal, Model model) {

        model.addAttribute("userShopViewModel", userService.getUserShopView(principal.getName()));

        return "shop";
    }

    @PostMapping("/shop/buy")
    public String buyChest(Principal principal, RedirectAttributes redirectAttributes) {

        String message = userService.buyChest(principal.getName());
        redirectAttributes
                .addFlashAttribute("shopMessage", message);

        return "redirect:buy";
    }
}
