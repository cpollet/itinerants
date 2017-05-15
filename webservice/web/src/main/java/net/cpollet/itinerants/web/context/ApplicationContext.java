package net.cpollet.itinerants.web.context;

import net.cpollet.itinerants.core.algorithm.AttendeeSelection;
import net.cpollet.itinerants.core.algorithm.SimpleAttendeeSelection;
import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.SaltedSha256PasswordHashingService;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by cpollet on 29.03.17.
 */
@Configuration
public class ApplicationContext {
    private static final String APPLICATION_NAME = "webservice-web";

    @Bean
    String applicationName() {
        return APPLICATION_NAME;
    }

    @Bean
    Password.PasswordHashingService passwordHashStrategy() {
        return new SaltedSha256PasswordHashingService();
    }

    @Bean
    Password.Factory passwordFactory() {
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
    Person.Factory personFactory(Password.Factory passwordFactory, Person.Notifier personNotifier) {
        return personData -> new Person(personData, passwordFactory, personNotifier);
    }

    @Bean
    AttendeeSelection.Factory attendeeSelectionFactory() {
        return SimpleAttendeeSelection::new;
    }

    @Bean
    CacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .build(true);
    }

    @Bean
    public Cache<String, String> passwordResetTokenCache(CacheManager cacheManager) {
        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100))
                .withExpiry(Expirations.timeToIdleExpiration(new Duration(8, TimeUnit.HOURS)))
                .build();

        return cacheManager.createCache("passwordResetTokenCache", cacheConfiguration);
    }
}
