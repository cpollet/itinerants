package net.cpollet.itinerants.ws.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class CreatePersonPayload {
    private final String name;
    private final String username;
    private final String password;
}
