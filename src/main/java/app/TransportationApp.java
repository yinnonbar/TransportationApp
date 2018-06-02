package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by yinnon on 31/05/2018.
 */
@SpringBootApplication
@EnableScheduling
public class TransportationApp {
    public static void main(String[] args) {
        SpringApplication.run(TransportationApp.class, args);

    }
}
