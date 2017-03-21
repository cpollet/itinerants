package net.cpollet.itinerants.rest.filters;

import net.cpollet.itinerants.rest.filters.configurer.AcceptVersionConfigurer;
import net.cpollet.itinerants.rest.filters.configurer.DefaultVersionConfigurer;
import net.cpollet.itinerants.rest.filters.configurer.HttpHeaderVersionConfigurer;
import net.cpollet.itinerants.rest.filters.configurer.PathVersionConfigurer;
import net.cpollet.itinerants.rest.filters.configurer.VersionConfigurerChain;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @author Christophe Pollet
 */
@Provider
@PreMatching
public class RequestVersionFilter implements ContainerRequestFilter {
    public static final String PROPERTY_VERSION = "version";

    private final VersionConfigurerChain versionConfigurerChain;

    public RequestVersionFilter() {
        versionConfigurerChain = new VersionConfigurerChain(
                new PathVersionConfigurer(),
                new AcceptVersionConfigurer(),
                new HttpHeaderVersionConfigurer(),
                new DefaultVersionConfigurer()
        );
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        versionConfigurerChain.before();
        versionConfigurerChain.configure(requestContext);
        versionConfigurerChain.after();
    }

}
