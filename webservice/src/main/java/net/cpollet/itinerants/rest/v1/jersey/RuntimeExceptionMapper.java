package net.cpollet.itinerants.rest.v1.jersey;

import net.cpollet.itinerants.rest.v1.data.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Christophe Pollet
 */
@Provider
public class RuntimeExceptionMapper extends BaseExceptionMapper<RuntimeException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException exception) {
        LOGGER.error("We got an exception with message: '{}'", exception.getMessage(), exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(new ErrorResponse("Internal server error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()))
                .type(getMediaType())
                .build();
    }
}
