package net.cpollet.itinerants.ws.api.v1.data;

import lombok.Data;
import net.cpollet.itinerants.ws.domain.data.PersonData;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class PersonResponse {
    private final Long personId;
    private final String name;
    private final String username;

    public PersonResponse(Long personId, String name, String username) {
        this.personId = personId;
        this.name = name;
        this.username = username;
    }

    public PersonResponse(Long personId, String name) {
        this(personId, name, null);
    }

    public PersonResponse(PersonData personData) {
        this(personData.getId(), personData.getName(), personData.getUsername());
    }
}
