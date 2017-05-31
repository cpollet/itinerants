package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

import java.util.List;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class CreatePersonPayload {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
    private final float targetRatio;
    private final List<String> roles;
}
