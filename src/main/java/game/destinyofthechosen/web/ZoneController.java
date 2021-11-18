package game.destinyofthechosen.web;

import game.destinyofthechosen.service.ZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping("/zones/{level}/find")
    public String findZones(@PathVariable Integer level, Principal principal, Model model) {

        model.addAttribute("zones", zoneService.getZonesInHeroLevelRange(principal.getName(), level));

        return "zones";
    }
}
