package net.cpollet.itinerants.mailer.context;

import net.cpollet.itinerants.mailer.handlers.NewAccountHandler;
import net.cpollet.itinerants.mailer.handlers.ResetPasswordHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cpollet on 11.05.17.
 */
@Configuration
public class ApplicationContext {
    private static final String APP_NAME = "mailer-daemon";

    @Bean
    String applicationName() {
        return APP_NAME;
    }

    @Bean
    NewAccountHandler newAccountHandler() {
        return new NewAccountHandler();
    }

    @Bean
    ResetPasswordHandler resetPasswordHandler() {
        return new ResetPasswordHandler();
    }
}
