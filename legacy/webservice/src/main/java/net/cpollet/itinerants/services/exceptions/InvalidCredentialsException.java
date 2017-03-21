package net.cpollet.itinerants.services.exceptions;

/**
 * @author Christophe Pollet
 */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
