package net.cpollet.itinerants.rest.filters.configurer;

import net.cpollet.itinerants.rest.Version;
import net.cpollet.itinerants.rest.filters.RequestVersionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author Christophe Pollet
 */
public class HttpHeaderVersionConfigurer extends BaseVersionConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHeaderVersionConfigurer.class);
    private static final String HTTP_HEADER_API_VERSION = "X-Version";

    @Override
    public void configure(ContainerRequestContext requestContext, VersionConfigurerChain chain) {
        Version version = decodeVersion(requestContext.getHeaderString(HTTP_HEADER_API_VERSION));
        if (version == null) {
            chain.configure(requestContext);
            return;
        }

        LOGGER.debug("Found version {}", version);
        requestContext.setProperty(RequestVersionFilter.PROPERTY_VERSION, version);
    }
}
