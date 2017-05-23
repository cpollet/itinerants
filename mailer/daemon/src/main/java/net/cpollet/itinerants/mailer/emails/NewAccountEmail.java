package net.cpollet.itinerants.mailer.emails;

import freemarker.template.Template;

import java.util.Map;

/**
 * Created by cpollet on 13.05.17.
 */
public class NewAccountEmail extends BaseEmail {
    public NewAccountEmail(Template template, Map<String, Object> defaultVariables) {
        super(template, defaultVariables);
    }

    @Override
    public String subject() throws EmailException {
        return "Nouveau compte";
    }
}
