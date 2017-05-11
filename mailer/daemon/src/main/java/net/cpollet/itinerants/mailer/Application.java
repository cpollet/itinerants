package net.cpollet.itinerants.mailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by cpollet on 11.05.17.
 */
@SpringBootApplication
@ComponentScan(basePackages = "net.cpollet.itinerants")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
