package net.cpollet.itinerants.mailer.handlers;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.NewAccountMessage;

/**
 * Created by cpollet on 12.05.17.
 */
@Slf4j
public class ResetPasswordHandler implements Handler<NewAccountMessage> {
    @Override
    public void handle(NewAccountMessage newAccountMessage) {
        log.info("Handling reset password for {}", newAccountMessage.getEmail());
    }
}
