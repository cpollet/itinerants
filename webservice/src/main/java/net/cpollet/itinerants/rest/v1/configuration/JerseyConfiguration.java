package net.cpollet.itinerants.rest.v1.configuration;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Christophe Pollet
 */
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        packages("net.cpollet.itinerants.rest.v1.resources", "net.cpollet.itinerants.rest.v1.jersey");
        register(JacksonFeature.class);
    }
}
