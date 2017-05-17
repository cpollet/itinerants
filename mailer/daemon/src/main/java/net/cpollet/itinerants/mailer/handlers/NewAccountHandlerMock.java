package net.cpollet.itinerants.mailer.handlers;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.NewAccountMessage;

/**
 * Created by cpollet on 17.05.17.
 */
@Slf4j
public class NewAccountHandlerMock implements Handler<NewAccountMessage> {
    @Override
    public void handle(NewAccountMessage payload) throws HandlerException {
        log.info(payload.toString());
    }

    @Override
    public Class<NewAccountMessage> handledMessage() {
        return NewAccountMessage.class;
    }
}
