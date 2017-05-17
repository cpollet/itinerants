package net.cpollet.itinerants.mailer;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.handlers.Handler;
import net.cpollet.itinerants.mailer.handlers.HandlerException;

import java.util.List;

/**
 * Created by cpollet on 13.05.17.
 */
@Slf4j
public class Receiver {
    private final List<Handler<?>> handlers;

    public Receiver(List<Handler<?>> handlers) {
        this.handlers = handlers;
    }

    public void handle(NewAccountMessage newAccountMessage) throws HandlerException {
        handlers.stream()
                .filter(h -> h.handledMessage().equals(NewAccountMessage.class))
                .forEach(h -> {
                    try {
                        //noinspection unchecked
                        ((Handler<NewAccountMessage>) h).handle(newAccountMessage);
                    } catch (HandlerException e) {
                        log.error("Handler {} threw an exception: {}", h.getClass(), e.getMessage(), e);
                    }
                });
    }
}
