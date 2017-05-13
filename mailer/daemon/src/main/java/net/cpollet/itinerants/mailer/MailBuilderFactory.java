package net.cpollet.itinerants.mailer;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.MailBuilder;

/**
 * Created by cpollet on 13.05.17.
 */
public class MailBuilderFactory {
    private final Configuration configuration;

    public MailBuilderFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public MailBuilder create() {
        return new MailBuilder(configuration);
    }
}
