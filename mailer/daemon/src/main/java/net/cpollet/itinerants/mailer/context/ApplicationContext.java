package net.cpollet.itinerants.mailer.context;

import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.cpollet.itinerants.mailer.Receiver;
import net.cpollet.itinerants.mailer.emails.NewAccountEmail;
import net.cpollet.itinerants.mailer.handlers.NewAccountHandler;
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
    NewAccountHandler newAccountHandler(MailBuilderFactory mailBuilderFactory, NewAccountEmail newAccountEmail) {
        return new NewAccountHandler(mailBuilderFactory, newAccountEmail);
    }

    @Bean
    Receiver receiver(NewAccountHandler newAccountHandler) {
        return new Receiver(newAccountHandler);
    }
}
