package game.destinyofthechosen.web;

import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.UserService;
import game.destinyofthechosen.service.ZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.UUID;

@Controller
public class EnemyController {

    private final HeroService heroService;
    private final ZoneService zoneService;
    private final EnemyService enemyService;

    public EnemyController(HeroService heroService, ZoneService zoneService, EnemyService enemyService) {
        this.heroService = heroService;
        this.zoneService = zoneService;
        this.enemyService = enemyService;
    }

    @GetMapping("/enemies/{id}/find")
    public String findEnemies(@PathVariable UUID id, Principal principal, Model model) {

        model.addAttribute("zone", zoneService.getZoneById(principal.getName(), id));

        return "enemies";
    }

    @GetMapping("/enemies/{id}/attack")
    public String attackEnemy(@PathVariable UUID id, Principal principal, Model model) {

        heroService.setCurrentHero(principal.getName());
        heroService.setCurrentEnemy(id);
        heroService.heroIsOverTheLevelRequirementForThatZone();

        model.addAttribute("enemy", enemyService.findById(id));
        model.addAttribute("hero", heroService.getCurrentHeroForCombat(principal.getName()));

        return "fight-enemy";
    }
}
