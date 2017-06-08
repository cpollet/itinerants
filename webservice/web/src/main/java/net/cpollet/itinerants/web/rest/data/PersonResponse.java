package net.cpollet.itinerants.web.rest.data;

import lombok.Data;
import net.cpollet.itinerants.core.domain.Person;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class PersonResponse {
    private final String personId;
    private final String name;

    public PersonResponse(String personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public PersonResponse(Person person) {
        this(person.id(), person.firstName() + " " + person.lastName());
    }
}
