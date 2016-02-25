package net.cpollet.itinerants.rest.resources;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.jersey.PATCH;
import net.cpollet.itinerants.rest.Version;
import net.cpollet.itinerants.rest.data.v1.PersonData;
import net.cpollet.itinerants.rest.filters.RequestVersionFilter;
import org.dozer.Mapper;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * @author Christophe Pollet
 */

@Path("persons")
public class PersonResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    private static final String ID = "id";

    private static final Class PERSONS = PersonResource.class;
    private static final Method GET_PERSON;

    static {
        try {
            GET_PERSON = PersonResource.class.getMethod("getPerson", String.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    private PersonService personService;

    @Inject
    private Mapper mapper;

    @Context
    private UriInfo uriInfo;

    @Context
    private Request request;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(PersonData personData) {
        String identifier = personService.hire(mapper.map(personData, Person.class));

        personData.link = personUrl(identifier).toString();

        return Response.created(personUrl(identifier)).entity(personData).build();
    }

    private URI personUrl(String identifier) {
        return uriInfo.getBaseUriBuilder()
                .path(PERSONS)
                .path(GET_PERSON)
                .resolveTemplate("id", identifier)
                .build();
    }

    private Version getVersion() {
        return (Version) ((ContainerRequest) request).getProperty(RequestVersionFilter.PROPERTY_VERSION);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonData getPerson(@PathParam(ID) String id) throws PersonNotFoundException {
        PersonData person = mapper.map(personService.getProfile(id), PersonData.class);
        person.link = personUrl(id).toString();
        return person;
    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response partialUpdatePerson(@PathParam(ID) String id, PersonData personData) throws PersonNotFoundException {
        personService.updateProfile(id, mapper.map(personData, Person.class));
        return Response.seeOther(personUrl(id)).build();
    }


    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam(ID) String id) throws PersonNotFoundException {
        personService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        LOGGER.info("Handling version {}", getVersion());
        return Response.ok(personService.getAll().stream()
                .map(e -> {
                    PersonData person = mapper.map(e, PersonData.class);
                    person.link = personUrl(e.getId()).toString();
                    return person;
                })
                .collect(Collectors.toList()))
                .build();
    }
}
