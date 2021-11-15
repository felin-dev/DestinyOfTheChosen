package game.destinyofthechosen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EnemyController {

    // TODO enemies by hero level range
    @GetMapping("/enemies/{level}/find")
    public String findEnemies(@PathVariable String level) {

        return "enemies";
    }
}
