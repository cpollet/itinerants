package net.cpollet.itinerants.rest.v1.jersey;

import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.rest.v1.data.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
@Provider
public class ExceptionMapper extends BaseExceptionMapper<Exception> {
    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);

    private final Map<Class<? extends Exception>, RestProperty> properties;

    public ExceptionMapper() {
        this.properties = new HashMap<>();
        properties.put(PersonNotFoundException.class, new RestProperty(404, 1000));
    }

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.info("We got an exception with message: '{}'", exception.getMessage(), exception);

        RestProperty property = properties.get(exception.getClass());

        return Response.status(property.httpResponseStatus).
                entity(new ErrorResponse(exception.getMessage(), property.code))
                .type(getMediaType())
                .build();
    }

    private class RestProperty {
        private final int httpResponseStatus;
        private final int code;

        public RestProperty(int httpResponseStatus, int code) {
            this.httpResponseStatus = httpResponseStatus;
            this.code = code;
        }
    }
}
