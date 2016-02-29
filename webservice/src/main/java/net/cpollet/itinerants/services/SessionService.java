package net.cpollet.itinerants.services;

import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.exceptions.InvalidCredentialsException;

/**
 * @author Christophe Pollet
 */
public interface SessionService {

    String create(String username, String password) throws InvalidCredentialsException, PersonNotFoundException;

    void destroy(String sessionId);

    boolean retrieve(String sessionId);
}
