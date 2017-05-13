package net.cpollet.itinerants.mailer;

import net.cpollet.itinerants.mailer.configuration.MailgunProperties;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.MailBuilder;

/**
 * Created by cpollet on 13.05.17.
 */
public class MailBuilderFactory {
    private final Configuration configuration;
    private final MailgunProperties mailgunProperties;

    public MailBuilderFactory(Configuration configuration, MailgunProperties mailgunProperties) {
        this.configuration = configuration;
        this.mailgunProperties = mailgunProperties;
    }

    public MailBuilder create() {
        return new MailBuilder(configuration) {
            @Override
            public MailBuilder subject(String subject) {
                return super.subject(mailgunProperties.getSubjectPrefix() + subject);
            }
        };
    }
}
