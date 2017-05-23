package net.cpollet.itinerants.mailer.context;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.cpollet.itinerants.mailer.Receiver;
import net.cpollet.itinerants.mailer.configuration.ApplicationProperties;
import net.cpollet.itinerants.mailer.emails.NewAccountEmail;
import net.cpollet.itinerants.mailer.emails.ResetPasswordEmail;
import net.cpollet.itinerants.mailer.handlers.Handler;
import net.cpollet.itinerants.mailer.handlers.NewAccountHandler;
import net.cpollet.itinerants.mailer.handlers.NewAccountHandlerMock;
import net.cpollet.itinerants.mailer.handlers.ResetPasswordHandler;
import net.cpollet.itinerants.mailer.handlers.PasswordResetHandlerMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * Created by cpollet on 11.05.17.
 */
@Slf4j
@Configuration
public class ApplicationContext {
    @Bean
    String applicationName(ApplicationProperties applicationProperties) {
        return applicationProperties.getName();
    }

    @Bean
    @Profile("mock")
    @Qualifier("newAccountHandler")
    NewAccountHandlerMock newAccountHandlerMock() {
        log.info("Creating {}", NewAccountHandlerMock.class);
        return new NewAccountHandlerMock();
    }

    @Bean
    @Profile("!mock")
    @Qualifier("newAccountHandler")
    NewAccountHandler newAccountHandler(MailBuilderFactory mailBuilderFactory, NewAccountEmail newAccountEmail) {
        log.info("Creating {}", NewAccountHandler.class);
        return new NewAccountHandler(mailBuilderFactory, newAccountEmail);
    }

    @Bean
    @Profile("mock")
    @Qualifier("passwordResetHandler")
    PasswordResetHandlerMock passwordResetHandlerMock() {
        log.info("Creating {}", PasswordResetHandlerMock.class);
        return new PasswordResetHandlerMock();
    }

    @Bean
    @Profile("!mock")
    @Qualifier("passwordResetHandler")
    ResetPasswordHandler passwordResetHandler(MailBuilderFactory mailBuilderFactory, ResetPasswordEmail resetPasswordTokenEmail) {
        log.info("Creating {}", ResetPasswordHandler.class);
        return new ResetPasswordHandler(mailBuilderFactory, resetPasswordTokenEmail);
    }

    @Bean
    Receiver receiver(List<Handler<?>> handlers) {
        return new Receiver(handlers);
    }
}
