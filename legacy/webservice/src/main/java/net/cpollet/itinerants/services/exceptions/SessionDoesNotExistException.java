package net.cpollet.itinerants.services.exceptions;

/**
 * @author Christophe Pollet
 */
public class SessionDoesNotExistException extends Exception {
    public SessionDoesNotExistException() {
        super("Session does not exist");
    }
}
