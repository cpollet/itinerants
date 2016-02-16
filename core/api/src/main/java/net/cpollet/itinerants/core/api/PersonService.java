package net.cpollet.itinerants.core.api;

import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;

/**
 * @author Christophe Pollet
 */
public interface PersonService {

    String hire(Person person);

    Person getInformation(String id) throws PersonNotFoundException;
}
