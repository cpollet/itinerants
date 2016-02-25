package net.cpollet.itinerants.rest.v1.jersey.filters.configurer;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author Christophe Pollet
 */
public interface VersionConfigurer {
    void configure(ContainerRequestContext requestContext, VersionConfigurerChain chain);
}
