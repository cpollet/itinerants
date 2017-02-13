package net.cpollet.itinerants.ws.api.v1.data;

import lombok.Data;
import net.cpollet.itinerants.ws.service.data.Person;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class PersonResponse {
    private final Long personId;
    private final String name;

    public PersonResponse(Long personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public PersonResponse(Person person) {
        this(person.getId(), person.getName());
    }
}
