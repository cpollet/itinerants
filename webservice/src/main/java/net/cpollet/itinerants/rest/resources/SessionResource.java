package net.cpollet.itinerants.rest.resources;

import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.exceptions.InvalidCredentialsException;
import net.cpollet.itinerants.rest.data.v1.Credentials;
import net.cpollet.itinerants.rest.data.v1.SessionData;
import net.cpollet.itinerants.services.SessionService;

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
import java.util.Collections;

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

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{token}")
    public Response getSession(@PathParam("token") String token) {
        return Response
                .ok()
                .entity(Collections.emptyList())
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credentials credentials) throws InvalidCredentialsException {
        try {
            String token = sessionService.create(credentials.username, credentials.password);
            SessionData sessionData = new SessionData();
            sessionData.token = token;
            sessionData.isValid = true;

            return Response
                    .created(sessionUrl(token))
                    .entity(sessionData)
                    .build();
        }
        catch (PersonNotFoundException e) {
            // we don't want to give information about the cause of the exception
            throw new InvalidCredentialsException();
        }
    }

    private URI sessionUrl(String identifier) {
        return uriInfo.getBaseUriBuilder()
                .path(SESSIONS)
                .path(GET_SESSION)
                .resolveTemplate("token", identifier)
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{token}")
    public Response logout(@PathParam("token") String token) {
        sessionService.destroy(token);
        return Response
                .ok()
                .build();
    }
}
