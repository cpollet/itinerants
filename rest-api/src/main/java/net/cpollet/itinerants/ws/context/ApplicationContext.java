package net.cpollet.itinerants.ws.context;

import net.cpollet.itinerants.ws.domain.Password;
import net.cpollet.itinerants.ws.domain.Person;
import net.cpollet.itinerants.ws.strategies.SaltedSha256PasswordHashingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cpollet on 29.03.17.
 */
@Configuration
public class ApplicationContext {
    @Bean
    public Password.PasswordHashingStrategy passwordHashStrategy() {
        return new SaltedSha256PasswordHashingStrategy();
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
}
