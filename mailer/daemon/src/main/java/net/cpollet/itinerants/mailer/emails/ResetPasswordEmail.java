package net.cpollet.itinerants.mailer.emails;

import freemarker.template.Template;

import java.util.Map;

/**
 * Created by cpollet on 23.05.17.
 */
public class ResetPasswordEmail extends BaseEmail {
    public ResetPasswordEmail(Template template, Map<String, Object> defaultVariables) {
        super(template, defaultVariables);
    }

    @Override
    public String subject() throws EmailException {
        return "Itinérants - Changement du mot de passe";
    }
}
