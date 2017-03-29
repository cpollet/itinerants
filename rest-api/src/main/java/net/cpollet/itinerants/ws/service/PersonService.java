package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.service.data.Person;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonService {
    Person getById(long id);

    long create(Person person);

    Person getByUsername(String username);

    abstract class InputPerson implements Person {
        @Override
        public Long getId() {
            throw new IllegalStateException();
        }
    }
}
