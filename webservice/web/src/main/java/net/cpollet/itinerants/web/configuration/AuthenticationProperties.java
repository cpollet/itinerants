package net.cpollet.itinerants.web.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cpollet on 15.05.17.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationProperties {
    private Integer sessionTokenTimeout;
    private Integer resetPasswordTokenTimeout;
}
