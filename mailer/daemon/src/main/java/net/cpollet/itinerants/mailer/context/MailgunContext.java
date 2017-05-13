package net.cpollet.itinerants.mailer.context;

import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.sargue.mailgun.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * Created by cpollet on 13.05.17.
 */
@org.springframework.context.annotation.Configuration
public class MailgunContext {
    @Bean
    Configuration mailgunConfiguration() {
        return new Configuration()
                .domain("mg.mubo.space")
                .apiKey(System.getProperty("apiKey"))
                .from("Christophe (Arena)", "itinerants@mg.mubo.space");
    }

    @Bean
    MailBuilderFactory mailBuilderFactory(Configuration configuration) {
        return new MailBuilderFactory(configuration);
    }
}
