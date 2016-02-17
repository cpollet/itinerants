package net.cpollet.itinerants.mocks;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Christophe Pollet
 */
public class PersonServiceMock implements PersonService {
    Map<String, Person> persons;

    public PersonServiceMock() {
        this.persons = new HashMap<>();
    }

    @Override
    public String hire(Person person) {
        String id = UUID.randomUUID().toString();

        Person savedPerson = new Person(id, person.getName(), person.getEmail());

        persons.put(id, savedPerson);

        return id;
    }

    @Override
    public Person getInformation(String id) throws PersonNotFoundException {
        if (persons.containsKey(id)) {
            return persons.get(id);
        }

        throw new PersonNotFoundException("Person with id [" + id + "] not found.");
    }

}
