package net.cpollet.itinerants.mailer.emails;

/**
 * Created by cpollet on 13.05.17.
 */
public class EmailException extends Exception {
    EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
