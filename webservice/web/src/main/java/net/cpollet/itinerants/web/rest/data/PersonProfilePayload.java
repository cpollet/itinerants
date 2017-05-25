package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 25.05.17.
 */
@Data
public class PersonProfilePayload {
    private final String firstName;
    private final String lastName;
    private final String email;
}
