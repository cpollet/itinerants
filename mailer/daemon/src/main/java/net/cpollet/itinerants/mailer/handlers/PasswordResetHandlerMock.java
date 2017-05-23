package net.cpollet.itinerants.mailer.handlers;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.ResetPasswordMessage;

/**
 * Created by cpollet on 23.05.17.
 */
@Slf4j
public class PasswordResetHandlerMock implements Handler<ResetPasswordMessage> {
    @Override
    public void handle(ResetPasswordMessage resetPasswordMessage) throws HandlerException {
        log.info(resetPasswordMessage.toString());
    }

    @Override
    public Class<ResetPasswordMessage> handledMessage() {
        return ResetPasswordMessage.class;
    }
}
