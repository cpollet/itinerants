package net.cpollet.itinerants.web.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

/**
 * Created by cpollet on 01.04.17.
 */
@Getter
@AllArgsConstructor
public class AuthenticationPrincipal implements Principal {
    private final String username;
    private final String personId;

    @Override
    public String getName() {
        return username;
    }
}
