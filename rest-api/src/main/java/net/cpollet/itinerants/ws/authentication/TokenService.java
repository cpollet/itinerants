package net.cpollet.itinerants.ws.authentication;

import org.springframework.security.core.Authentication;

/**
 * Created by cpollet on 15.03.17.
 */
public interface TokenService {

    void store(String token, Authentication authentication);

    boolean isTokenValid(String token);

    Authentication retrieve(String token);
}
