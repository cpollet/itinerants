package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 16.05.17.
 */
@Data
public class ChangePasswordPayload {
    private final String password1;
    private final String password2;
}
