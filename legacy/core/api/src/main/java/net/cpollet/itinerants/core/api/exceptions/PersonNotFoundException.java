package net.cpollet.itinerants.core.api.exceptions;

/**
 * @author Christophe Pollet
 */
public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
