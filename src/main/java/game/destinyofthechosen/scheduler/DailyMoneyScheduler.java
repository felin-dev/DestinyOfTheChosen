package game.destinyofthechosen.scheduler;

import game.destinyofthechosen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DailyMoneyScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyMoneyScheduler.class);
    private final UserService userService;

    public DailyMoneyScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 * * *")
    public void dailyMoneyIncome() {
        LOGGER.info("Daily money added at {}", LocalDateTime.now());
        userService.addDailyMoney();
    }
}
