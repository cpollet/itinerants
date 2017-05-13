package net.cpollet.itinerants.web.rest.data;

import lombok.Data;
import net.cpollet.itinerants.core.domain.data.PersonData;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class PersonResponse {
    private final String personId;
    private final String name;
    private final String username;

    public PersonResponse(String personId, String name, String username) {
        this.personId = personId;
        this.name = name;
        this.username = username;
    }

    public PersonResponse(String personId, String name) {
        this(personId, name, null);
    }

    public PersonResponse(PersonData personData) {
        this(personData.getUUID(), personData.getFirstName(), personData.getUsername());
    }
}
