package net.cpollet.itinerants.mailer.context;

import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.cpollet.itinerants.mailer.configuration.MailgunProperties;
import net.sargue.mailgun.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * Created by cpollet on 13.05.17.
 */
@org.springframework.context.annotation.Configuration
public class MailgunContext {
    @Bean
    Configuration mailgunConfiguration(MailgunProperties mailgunProperties) {
        return new Configuration()
                .domain(mailgunProperties.getDomain())
                .apiKey(mailgunProperties.getApikey())
                .from(mailgunProperties.getFrom().getName(), mailgunProperties.getFrom().getEmail());
    }

    @Bean
    MailBuilderFactory mailBuilderFactory(Configuration configuration, MailgunProperties mailgunProperties) {
        return new MailBuilderFactory(configuration, mailgunProperties);
    }
}
