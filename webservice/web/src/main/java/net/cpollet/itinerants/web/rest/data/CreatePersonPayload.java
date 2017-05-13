package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class CreatePersonPayload {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
}
