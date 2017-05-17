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
@ConfigurationProperties(prefix = "freemarker.variables")
public class FreeMarkerVariablesProperties {
    private String httpRoot;
    private String changePassword;
}
