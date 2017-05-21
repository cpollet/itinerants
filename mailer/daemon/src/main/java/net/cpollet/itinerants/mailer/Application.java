package net.cpollet.itinerants.mailer;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.configuration.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by cpollet on 11.05.17.
 */
@SpringBootApplication
@ComponentScan(basePackages = "net.cpollet.itinerants")
@Component
@Slf4j
public class Application {
    @Autowired
    private ApplicationProperties applicationProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    void onStartup(ApplicationReadyEvent event) {
        log.info("{} {} ready", applicationProperties.getName(), applicationProperties.getVersion());
    }
}
