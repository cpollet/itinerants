package net.cpollet.itinerants.rest.v1.jersey.filters.configurer;

import net.cpollet.itinerants.rest.Version;
import net.cpollet.itinerants.rest.v1.jersey.filters.RequestVersionFilter;
import net.cpollet.itinerants.rest.v1.jersey.filters.ResponseVersionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Christophe Pollet
 */
public class AcceptVersionConfigurer extends BaseVersionConfigurer {
    private static final String ACCEPT_TEMPLATE = "application/vnd.itinerants.%s+json";
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptVersionConfigurer.class);
    private static final Pattern ACCEPT_PATTERN = Pattern.compile("application/vnd\\.itinerants\\.([a-z0-9]+)\\+json");

    @Override
    public void configure(ContainerRequestContext requestContext, VersionConfigurerChain chain) {
        Version version = decodeVersion(requestContext.getAcceptableMediaTypes().stream()
                .map(MediaType::toString)
                .map(ACCEPT_PATTERN::matcher)
                .map(e -> e.find() ? e.group(1) : null)
                .filter(e -> e != null)
                .findFirst()
                .orElse(null));

        if (version == null) {
            chain.configure(requestContext);
            return;
        }

        LOGGER.debug("Found version {}", version);
        requestContext.setProperty(RequestVersionFilter.PROPERTY_VERSION, version);

        Map<String, List<Object>> headers= new HashMap<>(2);
        headers.put("Content-Type",Collections.singletonList(String.format(ACCEPT_TEMPLATE, version.version)));

        requestContext.setProperty(ResponseVersionFilter.VERSION_HEADERS, headers);

        requestContext.getHeaders().put("Accept", Collections.singletonList("application/json"));
    }
}
