package net.cpollet.itinerants.web.context;

import net.cpollet.itinerants.core.algorithm.Attendee;
import net.cpollet.itinerants.core.algorithm.AttendeeSelection;
import net.cpollet.itinerants.core.algorithm.SimpleAttendeeSelection;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.SaltedSha256PasswordHashingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

/**
 * Created by cpollet on 29.03.17.
 */
@Configuration
public class ApplicationContext {
    @Bean
    public Password.PasswordHashingService passwordHashStrategy() {
        return new SaltedSha256PasswordHashingService();
    }

    @Bean
    public Password.Factory passwordFactory() {
        return new Password.Factory() {
            @Override
            public Password create(String hashedPassword) {
                return new Password(hashedPassword, passwordHashStrategy());
            }

            @Override
            public Password hash(String password) {
                return create(passwordHashStrategy().hash(password));
            }
        };
    }

    @Bean
    public Person.Factory personFactory() {
        return personData -> new Person(personData, passwordFactory());
    }

    @Bean
    public AttendeeSelection.Factory attendeeSelectionFactory() {
        return SimpleAttendeeSelection::new;
    }
}
