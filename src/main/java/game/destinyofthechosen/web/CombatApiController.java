package game.destinyofthechosen.web;

import game.destinyofthechosen.model.view.CombatStatusViewModel;
import game.destinyofthechosen.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CombatApiController {

    private final UserService userService;

    public CombatApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/enemies/attack")
    public ResponseEntity<CombatStatusViewModel> attackEnemy(Principal principal) {

        CombatStatusViewModel combatStatus = userService.performAttackOnEnemy(principal.getName());

        return ResponseEntity
                .ok(combatStatus);
    }
}
