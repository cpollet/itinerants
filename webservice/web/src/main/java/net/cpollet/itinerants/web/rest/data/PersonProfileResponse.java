package net.cpollet.itinerants.web.rest.data;

import lombok.Data;
import net.cpollet.itinerants.core.domain.Person;

/**
 * Created by cpollet on 24.05.17.
 */
@Data
public class PersonProfileResponse {
    private final String firstName;
    private final String lastName;
    private final String email;

    public PersonProfileResponse(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public PersonProfileResponse(Person person) {
        this(person.firstName(), person.lastName(), person.email());
    }
}
