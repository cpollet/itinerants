package net.cpollet.itinerants.mailer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cpollet on 13.05.17.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mailgun")
public class MailgunProperties {

    private From from;
    private String domain;
    private String apikey;
    private String subjectPrefix;

    @Getter
    @Setter
    public static class From {
        private String name;
        private String email;
    }
}
