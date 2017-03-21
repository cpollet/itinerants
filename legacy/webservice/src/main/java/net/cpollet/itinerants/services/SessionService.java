package net.cpollet.itinerants.services;

import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.services.exceptions.InvalidCredentialsException;
import net.cpollet.itinerants.services.data.Session;
import net.cpollet.itinerants.services.exceptions.SessionDoesNotExistException;

/**
 * @author Christophe Pollet
 */
public interface SessionService {
    String create(String username, String password) throws InvalidCredentialsException, PersonNotFoundException;

    Session sessionDetail(String sessionId) throws SessionDoesNotExistException;

    void destroy(String sessionId);
}
