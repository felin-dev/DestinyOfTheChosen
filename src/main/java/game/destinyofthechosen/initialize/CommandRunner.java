package game.destinyofthechosen.initialize;

import game.destinyofthechosen.service.UserRoleService;
import game.destinyofthechosen.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public CommandRunner(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userRoleService.initialize();
        userService.initialize();
    }
}
