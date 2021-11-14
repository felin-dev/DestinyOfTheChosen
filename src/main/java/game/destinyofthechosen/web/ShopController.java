package game.destinyofthechosen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    // TODO shop by hero level range
    @GetMapping("/shop/buy")
    public String openShop() {

        return "shop";
    }
}
