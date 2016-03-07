package net.cpollet.itinerants.rest.resources;

import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.services.exceptions.InvalidCredentialsException;
import net.cpollet.itinerants.rest.data.v1.Credentials;
import net.cpollet.itinerants.rest.data.v1.SessionData;
import net.cpollet.itinerants.services.SessionService;
import net.cpollet.itinerants.services.exceptions.SessionDoesNotExistException;
import org.dozer.Mapper;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author Christophe Pollet
 */
@Path("sessions")
public class SessionResource {

    private static final Class SESSIONS = SessionResource.class;
    private static final Method GET_SESSION;

    static {
        try {
            GET_SESSION = SessionResource.class.getMethod("getSession", String.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    private SessionService sessionService;

    @Inject
    private Mapper mapper;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{sessionId}")
    public Response getSession(@PathParam("sessionId") String sessionId) throws SessionDoesNotExistException {
        SessionData sessionData = getSessionData(sessionId);

        return Response
                .ok()
                .entity(sessionData)
                .build();
    }

    private SessionData getSessionData(String sessionId) throws SessionDoesNotExistException {
        SessionData sessionData = mapper.map(sessionService.sessionDetail(sessionId), SessionData.class);

        sessionData.isValid = true;
        sessionData.sessionId = sessionId;
        return sessionData;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credentials credentials) throws InvalidCredentialsException {
        try {
            String sessionId = sessionService.create(credentials.username, credentials.password);

            SessionData sessionData = getSessionData(sessionId);

            return Response
                    .created(sessionUrl(sessionId))
                    .entity(sessionData)
                    .build();
        }
        catch (PersonNotFoundException e) {
            // we don't want to give information about the cause of the exception
            throw new InvalidCredentialsException();
        }
        catch (SessionDoesNotExistException e) {
            throw new IllegalStateException();
        }
    }

    private URI sessionUrl(String sessionId) {
        return uriInfo.getBaseUriBuilder()
                .path(SESSIONS)
                .path(GET_SESSION)
                .resolveTemplate("sessionId", sessionId)
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{sessionId}")
    public Response logout(@PathParam("sessionId") String sessionId) {
        sessionService.destroy(sessionId);
        return Response
                .ok()
                .build();
    }
}
