package game.destinyofthechosen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnemyController {

    // TODO enemies by hero level range
    @GetMapping("/enemies/find")
    public String findEnemies() {

        return "enemies";
    }
}
