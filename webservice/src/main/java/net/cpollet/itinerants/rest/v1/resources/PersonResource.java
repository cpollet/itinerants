package net.cpollet.itinerants.rest.v1.resources;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.jersey.PATCH;
import net.cpollet.itinerants.rest.v1.data.PersonData;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonData getPerson(@PathParam(ID) String id) {
        try {
            PersonData person = mapper.map(personService.getProfile(id), PersonData.class);
            person.link = personUrl(id).toString();
            return person;
        }
        catch (PersonNotFoundException e) {
            throw new RestException(e, RestException.ERROR_PERSON_NOT_FOUND, Response.Status.NOT_FOUND);
        }
    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response partialUpdatePerson(@PathParam(ID) String id, PersonData personData) {
        try {
            personService.updateProfile(id, mapper.map(personData, Person.class));
            return Response.seeOther(personUrl(id)).build();
        }
        catch (PersonNotFoundException e) {
            throw new RestException(e, RestException.ERROR_PERSON_NOT_FOUND, Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam(ID) String id, PersonData personData) {
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam(ID) String id) {
        try {
            personService.delete(id);
        }
        catch (PersonNotFoundException e) {
            throw new RestException(e, RestException.ERROR_PERSON_NOT_FOUND, Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonData> getAllPersons() {
        return personService.getAll().stream()
                .map(e -> {
                    PersonData person = mapper.map(e, PersonData.class);
                    person.link = personUrl(e.getId()).toString();
                    return person;
                })
                .collect(Collectors.toList());
    }
}
