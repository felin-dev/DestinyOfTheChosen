package game.destinyofthechosen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DestinyOfTheChosenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DestinyOfTheChosenApplication.class, args);
    }

}
