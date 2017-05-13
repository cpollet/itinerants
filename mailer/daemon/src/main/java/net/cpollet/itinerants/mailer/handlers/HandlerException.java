package net.cpollet.itinerants.mailer.handlers;

/**
 * Created by cpollet on 13.05.17.
 */
public class HandlerException extends Exception {
    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
