package net.cpollet.itinerants.mocks;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;

import java.util.Collection;
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
        hire(new Person(null, "First", "first@example.com"));
    }

    @Override
    public String hire(Person person) {
        String id = UUID.randomUUID().toString();

        Person savedPerson = new Person(id, person.getName(), person.getEmail());

        persons.put(id, savedPerson);

        return id;
    }

    @Override
    public Person getProfile(String id) throws PersonNotFoundException {
        if (persons.containsKey(id)) {
            return persons.get(id);
        }

        throw new PersonNotFoundException("Person with id [" + id + "] not found.");
    }

    @Override
    public void updateProfile(String id, Person person) throws PersonNotFoundException {
        assertPersonExists(id);

        Person current = persons.get(id);

        Person updatedPerson = new Person(id,
                person.getName() == null ? current.getName() : person.getName(),
                person.getEmail() == null ? current.getEmail() : person.getEmail());

        persons.put(id, updatedPerson);
    }

    private void assertPersonExists(String id) throws PersonNotFoundException {
        if (!persons.containsKey(id)) {
            throw new PersonNotFoundException("Person with id [" + id + "] not found.");
        }
    }

    @Override
    public void delete(String id) throws PersonNotFoundException {
        assertPersonExists(id);

        persons.remove(id);
    }

    @Override
    public Collection<Person> getAll() {
        return persons.values();
    }


}
