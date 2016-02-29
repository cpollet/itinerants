package net.cpollet.itinerants.services;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.core.api.exceptions.PersonNotFoundException;
import net.cpollet.itinerants.exceptions.InvalidCredentialsException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Christophe Pollet
 */
public class InMemorySessionService implements SessionService {
    private final Map<String, String> sessions;
    private final PersonService personService;

    public InMemorySessionService(PersonService personService) {
        this.personService = personService;
        this.sessions = new ConcurrentHashMap<>();
    }

    @Override
    public String create(String username, String password) throws InvalidCredentialsException, PersonNotFoundException {
        if (!personService.isPasswordValid(username, password)) {
            throw new InvalidCredentialsException();
        }

        Person profile = personService.getProfileByUsername(username);

        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, profile.getId());
        return sessionId;
    }

    @Override
    public void destroy(String sessionId) {
        sessions.remove(sessionId);
    }

    @Override
    public boolean retrieve(String sessionId) {
        return sessions.containsKey(sessionId);
    }
}
