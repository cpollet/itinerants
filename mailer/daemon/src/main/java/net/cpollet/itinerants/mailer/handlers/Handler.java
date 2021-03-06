package net.cpollet.itinerants.mailer.handlers;

/**
 * Created by cpollet on 13.05.17.
 */
public interface Handler<T> {
    void handle(T payload) throws HandlerException;

    Class<T> handledMessage();
}
