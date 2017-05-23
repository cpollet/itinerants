package net.cpollet.itinerants.mailer.emails;

import freemarker.template.Template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by cpollet on 23.05.17.
 */
public class ResetPasswordEmail implements Email {
    private final Template template;
    private final Map<String, Object> defaultVariables;

    public ResetPasswordEmail(Template template, Map<String, Object> defaultVariables) {
        this.template = template;
        this.defaultVariables = defaultVariables;
    }

    @Override
    public String content(Map<String, Object> variables) throws EmailException {
        StringWriter stringWriter = new StringWriter();

        processContent(new DataModel(Arrays.asList(variables, defaultVariables)), stringWriter);

        return stringWriter.toString();
    }

    private void processContent(DataModel variables, Writer writer) throws EmailException {
        try {
            template.process(variables, writer);
        } catch (Exception e) {
            throw new EmailException("Unable to create email content", e);
        }
    }

    @Override
    public String subject() throws EmailException {
        return "Changement du mot de passe";
    }
}
