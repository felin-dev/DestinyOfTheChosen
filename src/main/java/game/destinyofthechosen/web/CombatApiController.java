package game.destinyofthechosen.web;

import game.destinyofthechosen.model.dto.SkillDto;
import game.destinyofthechosen.model.view.CombatStatusViewModel;
import game.destinyofthechosen.model.view.CsrfTokenViewModel;
import game.destinyofthechosen.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class CombatApiController {

    private final UserService userService;

    public CombatApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/csrf")
    public ResponseEntity<CsrfTokenViewModel> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return ResponseEntity.ok(new CsrfTokenViewModel(csrf.getHeaderName(), csrf.getToken()));
    }

    @PostMapping("/enemies/attack")
    public ResponseEntity<CombatStatusViewModel> attackEnemy(Principal principal) {

        CombatStatusViewModel combatStatus = userService.performAttackOnEnemy(principal.getName());

        return ResponseEntity
                .ok(combatStatus);
    }

    @PostMapping("/enemies/skill/attack")
    public ResponseEntity<CombatStatusViewModel> skillAttackEnemy(@RequestBody SkillDto skillDto, Principal principal) {

        CombatStatusViewModel combatStatus = userService.castSkillOnEnemy(principal.getName(), skillDto.getSkillName());

        return ResponseEntity
                .ok(combatStatus);
    }

    @GetMapping("/enemies/attack/new-enemy")
    public ResponseEntity<CombatStatusViewModel> attackNewEnemy(Principal principal) {

        userService.setCurrentHero(principal.getName());
        CombatStatusViewModel combatStatus = userService.resetCurrentEnemy();

        return ResponseEntity
                .ok(combatStatus);
    }
}
