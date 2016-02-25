package net.cpollet.itinerants.rest.filters.configurer;

import net.cpollet.itinerants.rest.Version;
import net.cpollet.itinerants.rest.filters.RequestVersionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.PathSegment;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Christophe Pollet
 */
public class PathVersionConfigurer extends BaseVersionConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PathVersionConfigurer.class);

    @Override
    public void configure(ContainerRequestContext requestContext, VersionConfigurerChain chain) {
        List<PathSegment> segments = requestContext.getUriInfo().getPathSegments();

        Version version = decodeVersion(segments.get(0).getPath());
        if (version == null) {
            chain.configure(requestContext);
            return;
        }

        LOGGER.debug("Found version {}", version);
        requestContext.setProperty(RequestVersionFilter.PROPERTY_VERSION, version);
        String newURI = segments.stream()
                .skip(1)
                .map(PathSegment::getPath)
                .collect(Collectors.joining("/", "/", ""));
        try {
            requestContext.setRequestUri(new URI(newURI));
        }
        catch (URISyntaxException e) {
            throw new IllegalStateException();
        }
    }


}
