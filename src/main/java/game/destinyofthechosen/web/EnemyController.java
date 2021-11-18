package game.destinyofthechosen.web;

import game.destinyofthechosen.service.ZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.UUID;

@Controller
public class EnemyController {

    private final ZoneService zoneService;

    public EnemyController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping("/enemies/{id}/find")
    public String findEnemies(@PathVariable UUID id, Principal principal, Model model) {

        model.addAttribute("zone", zoneService.getZoneById(principal.getName(), id));

        return "enemies";
    }

    // TODO attack enemies
    @GetMapping("/enemies/{id}/attack")
    public String attackEnemy(@PathVariable UUID id, Principal principal, Model model) {

        return "enemies";
    }
}
