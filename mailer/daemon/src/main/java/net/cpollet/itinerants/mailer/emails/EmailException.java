package net.cpollet.itinerants.mailer.emails;

/**
 * Created by cpollet on 13.05.17.
 */
public class EmailException extends Throwable {
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
