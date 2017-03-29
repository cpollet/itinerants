package net.cpollet.itinerants.ws.domain;

import net.cpollet.itinerants.ws.domain.data.PersonData;

/**
 * Created by cpollet on 29.03.17.
 */
public class Person {
    private final PersonData personData;
    private final Password.Factory passwordFactory;

    public Person(PersonData personData, Password.Factory passwordFactory) {
        this.personData = personData;
        this.passwordFactory = passwordFactory;
    }

    public Password password() {
        return passwordFactory.create(personData.getPassword());
    }

    public interface Factory {
        Person create(PersonData personData);
    }
}
