package net.cpollet.itinerants.core.api;

import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;

import java.util.Collection;

/**
 * @author Christophe Pollet
 */
public interface PersonService {

    String hire(Person person);

    Person getProfile(String id) throws PersonNotFoundException;

    void updateProfile(String id, Person person) throws PersonNotFoundException;

    void delete(String id) throws PersonNotFoundException;

    Collection<Person> getAll();
}
