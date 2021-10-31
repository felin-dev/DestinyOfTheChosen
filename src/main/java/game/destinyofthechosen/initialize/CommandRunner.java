package game.destinyofthechosen.initialize;

import game.destinyofthechosen.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

    private final UserRoleService userRoleService;

    public CommandRunner(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) {
        userRoleService.initialize();
    }
}
