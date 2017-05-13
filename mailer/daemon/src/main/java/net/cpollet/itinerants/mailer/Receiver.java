package net.cpollet.itinerants.mailer;

import net.cpollet.itinerants.mailer.handlers.HandlerException;
import net.cpollet.itinerants.mailer.handlers.NewAccountHandler;

/**
 * Created by cpollet on 13.05.17.
 */
public class Receiver {
    private final NewAccountHandler newAccountHandler;

    public Receiver(NewAccountHandler newAccountHandler) {
        this.newAccountHandler = newAccountHandler;
    }

    public void handle(NewAccountMessage newAccountMessage) throws HandlerException {
        newAccountHandler.handle(newAccountMessage);
    }
}
