package net.cpollet.itinerants.rest.v1.resources;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.rest.v1.data.Person;
import net.cpollet.itinerants.rest.v1.exceptions.RestException;
import org.dozer.Mapper;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * @author Christophe Pollet
 */
@Path("persons")
public class PersonResource {
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
    PersonService personService;

    @Inject
    Mapper mapper;

    @Context
    private UriInfo uriInfo;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) throws URISyntaxException {
        String identifier = personService.hire(mapper.map(person, net.cpollet.itinerants.core.api.data.Person.class));

        URI uri = uriInfo.getBaseUriBuilder()
                .path(PERSONS)
                .path(GET_PERSON)
                .resolveTemplate("id", identifier)
                .build();

        person.setId(identifier);

        return Response.created(uri).entity(person).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson(@PathParam(ID) String id) {
        try {
            return mapper.map(personService.getInformation(id), Person.class);
        }
        catch (PersonNotFoundException e) {
            throw new RestException(e, RestException.ERROR_USER_NOT_FOUND, Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person updatePerson(@PathParam(ID) String id, Person person) {
        return null;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Person deletePerson(@PathParam(ID) String id) {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        return Collections.emptyList();
    }
}
