package net.cpollet.itinerants.core.domain;

import net.cpollet.itinerants.core.domain.data.PersonData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> roles() {
        return Arrays.stream(personData.getRoles().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public String id() {
        return  personData.getUUID();
    }

    public interface Factory {
        Person create(PersonData personData);
    }
}
