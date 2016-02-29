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
    private final Map<String, Person> persons;
    private final Map<String, String> passwords;

    public PersonServiceMock() {
        this.persons = new HashMap<>();
        this.passwords = new HashMap<>();
        String personId = hire(new Person(null, "First", "first@example.com"));
        try {
            setPassword(personId, "password");
        }
        catch (PersonNotFoundException e) {
            throw new IllegalStateException();
        }
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
        assertPersonExists(id);

        return persons.get(id);
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

    @Override
    public void setPassword(String identifier, String password) throws PersonNotFoundException {
        assertPersonExists(identifier);
        passwords.put(identifier, password);
    }

    @Override
    public boolean isPasswordValid(String username, String password) throws PersonNotFoundException {
        for (Map.Entry<String, Person> personEntry : persons.entrySet()) {
            if (personEntry.getValue().getEmail().equals(username)) {
                if (passwords.get(personEntry.getKey()).equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Person getProfileByUsername(String username) throws PersonNotFoundException {
        for (Map.Entry<String, Person> personEntry : persons.entrySet()) {
            if (personEntry.getValue().getEmail().equals(username)) {
                return personEntry.getValue();
            }
        }
        throw new PersonNotFoundException("Person with username [" + username + "] not found");
    }
}
