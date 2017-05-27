package net.cpollet.itinerants.web;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.web.authentication.AuthenticationPrincipal;
import net.cpollet.itinerants.web.authentication.TokenService;
import net.cpollet.itinerants.web.configuration.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by cpollet on 11.02.17.
 */
@SpringBootApplication
@ComponentScan(basePackages = "net.cpollet.itinerants")
@Component
@Slf4j
public class Application {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApplicationProperties applicationProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    void onStartup(ApplicationReadyEvent event) {
        log.info("{} {} ready", applicationProperties.getName(), applicationProperties.getVersion());

        if (System.getProperty("dummySession", "false").equals("true")) {
            log.warn("!!! CREATING A FAKE ADMIN SESSION FOR TOKEN 'admin' !!!");
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    new AuthenticationPrincipal("cpollet", "4249085e-3f22-11e7-a919-92ebcb67fe33"),
                    "", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")));

            tokenService.store("admin", authentication);

            log.warn("!!! CREATING A FAKE USER SESSION FOR TOKEN 'user' !!!");
            authentication = new UsernamePasswordAuthenticationToken(
                    new AuthenticationPrincipal("user", "a646a4e2-0b2e-4af2-9b38-e31d632a0d7c"),
                    "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

            tokenService.store("user", authentication);
        }
    }
}
