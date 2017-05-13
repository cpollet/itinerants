package net.cpollet.itinerants.mailer.context;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import net.cpollet.itinerants.mailer.configuration.FreeMarkerVariablesProperties;
import net.cpollet.itinerants.mailer.emails.NewAccountEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by cpollet on 13.05.17.
 */
@org.springframework.context.annotation.Configuration
public class FreemarkerContext {
    @Autowired
    private FreeMarkerVariablesProperties freeMarkerVariablesProperties;

    @Bean
    Configuration freemarkerConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

        configuration.setClassForTemplateLoading(this.getClass(), "/freemarker");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.forLanguageTag("fr-CH"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return configuration;
    }

    @Bean
    NewAccountEmail newAccountEmail(Configuration configuration) throws IOException {
        Template template = configuration.getTemplate("newAccount.ftl");

        Map<String, Object> defaultValues = new HashMap<>();
        defaultValues.put("httpRoot", freeMarkerVariablesProperties.getHttpRoot());

        return new NewAccountEmail(template, defaultValues);
    }
}
