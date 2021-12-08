package game.destinyofthechosen.initialize;

import game.destinyofthechosen.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final ZoneService zoneService;
    private final ItemService itemService;
    private final EnemyService enemyService;
    private final SkillService skillService;
    private final HeroService heroService;

    public CommandRunner(UserRoleService userRoleService, UserService userService, ZoneService zoneService,
                         ItemService itemService, EnemyService enemyService, SkillService skillService,
                         HeroService heroService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.zoneService = zoneService;
        this.itemService = itemService;
        this.enemyService = enemyService;
        this.skillService = skillService;
        this.heroService = heroService;
    }

    @Override
    public void run(String... args) {
        userRoleService.initialize();
        userService.initialize();
        zoneService.initialize();
        itemService.initialize();
        enemyService.initialize();
        skillService.initialize();
        heroService.initialize();
    }
}
