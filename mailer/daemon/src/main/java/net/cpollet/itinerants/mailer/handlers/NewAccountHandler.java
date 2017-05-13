package net.cpollet.itinerants.mailer.handlers;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.MailBuilderFactory;
import net.cpollet.itinerants.mailer.NewAccountMessage;

/**
 * Created by cpollet on 12.05.17.
 */
@Slf4j
public class NewAccountHandler implements Handler<NewAccountMessage> {
    private final MailBuilderFactory mailBuilderFactory;

    public NewAccountHandler(MailBuilderFactory mailBuilderFactory) {
        this.mailBuilderFactory = mailBuilderFactory;
    }

    @Override
    public void handle(NewAccountMessage newAccountMessage) {
        log.info("Handling new account for {}", newAccountMessage.getEmail());

        mailBuilderFactory.create()
                .to(newAccountMessage.getEmail())
                .subject("Nouveau compte")
                .text("Token: " + newAccountMessage.getToken())
                .build()
                .send();
    }
}
