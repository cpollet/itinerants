package net.cpollet.itinerants.mailer.handlers;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.cpollet.itinerants.mailer.ResetPasswordMessage;
import net.cpollet.itinerants.mailer.emails.EmailException;
import net.cpollet.itinerants.mailer.emails.ResetPasswordEmail;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cpollet on 23.05.17.
 */
@Slf4j
public class ResetPasswordHandler implements Handler<ResetPasswordMessage> {
    private final MailBuilderFactory mailBuilderFactory;
    private final ResetPasswordEmail resetPasswordEmail;

    public ResetPasswordHandler(MailBuilderFactory mailBuilderFactory, ResetPasswordEmail resetPasswordEmail) {
        this.mailBuilderFactory = mailBuilderFactory;
        this.resetPasswordEmail = resetPasswordEmail;
    }

    @Override
    public void handle(ResetPasswordMessage resetPasswordMessage) throws HandlerException {
        log.info("Handling password reset token for {}", resetPasswordMessage.getEmail());

        String content = content(resetPasswordMessage);
        String subject = subject();

        mailBuilderFactory.create()
                .to(resetPasswordMessage.getEmail())
                .subject(subject)
                .text(content)
                .build()
                .send();
    }

    private String content(ResetPasswordMessage resetPasswordMessage) throws HandlerException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("token", resetPasswordMessage.getToken());
        variables.put("username", resetPasswordMessage.getUsername());

        try {
            return resetPasswordEmail.content(variables);
        } catch (EmailException e) {
            throw new HandlerException("Unable to handle new account message", e);
        }
    }

    private String subject() throws HandlerException {
        try {
            return resetPasswordEmail.subject();
        } catch (EmailException e) {
            throw new HandlerException("Unable to handle new account message", e);
        }
    }

    @Override
    public Class<ResetPasswordMessage> handledMessage() {
        return ResetPasswordMessage.class;
    }
}
