package net.cpollet.itinerants.mailer.handlers;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.cpollet.itinerants.mailer.NewAccountMessage;
import net.cpollet.itinerants.mailer.emails.EmailException;
import net.cpollet.itinerants.mailer.emails.NewAccountEmail;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cpollet on 12.05.17.
 */
@Slf4j
public class NewAccountHandler implements Handler<NewAccountMessage> {
    private final MailBuilderFactory mailBuilderFactory;
    private final NewAccountEmail newAccountEmail;

    public NewAccountHandler(MailBuilderFactory mailBuilderFactory, NewAccountEmail newAccountEmail) {
        this.mailBuilderFactory = mailBuilderFactory;
        this.newAccountEmail = newAccountEmail;
    }

    @Override
    public void handle(NewAccountMessage newAccountMessage) throws HandlerException {
        log.info("Handling new account for {}", newAccountMessage.getEmail());

        String content = content(newAccountMessage);
        String subject = subject();

        mailBuilderFactory.create()
                .to(newAccountMessage.getEmail())
                .subject(subject)
                .text(content)
                .build()
                .send();
    }

    private String content(NewAccountMessage newAccountMessage) throws HandlerException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("token", newAccountMessage.getToken());
        variables.put("username", newAccountMessage.getUsername());

        try {
            return newAccountEmail.content(variables);
        } catch (EmailException e) {
            throw new HandlerException("Unable to handle new account message", e);
        }
    }

    private String subject() throws HandlerException {
        try {
            return newAccountEmail.subject();
        } catch (EmailException e) {
            throw new HandlerException("Unable to handle new account message", e);
        }
    }
}
