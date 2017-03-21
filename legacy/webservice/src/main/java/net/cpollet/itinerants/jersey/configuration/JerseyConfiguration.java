package net.cpollet.itinerants.jersey.configuration;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Christophe Pollet
 */
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        packages("net.cpollet.itinerants.rest");
        register(JacksonFeature.class);
    }
}
