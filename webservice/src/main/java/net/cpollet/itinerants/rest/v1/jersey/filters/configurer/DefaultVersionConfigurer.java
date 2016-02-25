package net.cpollet.itinerants.rest.v1.jersey.filters.configurer;

import net.cpollet.itinerants.rest.Version;
import net.cpollet.itinerants.rest.v1.jersey.filters.RequestVersionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author Christophe Pollet
 */
public class DefaultVersionConfigurer implements VersionConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultVersionConfigurer.class);

    @Override
    public void configure(ContainerRequestContext requestContext, VersionConfigurerChain chain) {
        LOGGER.debug("Fallback to default version: {}", Version.V_1);
        requestContext.setProperty(RequestVersionFilter.PROPERTY_VERSION, Version.V_1);
    }
}
