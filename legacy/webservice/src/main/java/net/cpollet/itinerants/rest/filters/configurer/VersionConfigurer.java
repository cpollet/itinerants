package net.cpollet.itinerants.rest.filters.configurer;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author Christophe Pollet
 */
public interface VersionConfigurer {
    void configure(ContainerRequestContext requestContext, VersionConfigurerChain chain);
}
