package net.cpollet.itinerants.rest.filters;

import net.cpollet.itinerants.rest.Version;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
@Provider
public class ResponseVersionFilter implements ContainerResponseFilter {
    public static final String VERSION_HEADERS = "version.headers";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Version version = (Version) requestContext.getProperty(RequestVersionFilter.PROPERTY_VERSION);

        responseContext.getHeaders().put("X-Version", Collections.singletonList(version.version));

        if (requestContext.getProperty(VERSION_HEADERS) != null) {
            @SuppressWarnings("unchecked")
            Map<String, List<Object>> headers = (Map<String, List<Object>>) requestContext.getProperty(VERSION_HEADERS);
            for (Map.Entry<String, List<Object>> header : headers.entrySet()) {
                responseContext.getHeaders().put(header.getKey(), header.getValue());
            }
        }
    }
}
