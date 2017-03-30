package net.cpollet.itinerants.ws.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 16.03.17.
 */
@Data
public class LoginPayload {
    private String username;
    private String password;
}
