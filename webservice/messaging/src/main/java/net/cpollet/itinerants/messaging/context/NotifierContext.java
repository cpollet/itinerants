package net.cpollet.itinerants.messaging.context;

import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.messaging.RabbitPersonNotifier;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cpollet on 13.05.17.
 */
@Configuration
public class NotifierContext {
    @Bean
    Person.Notifier personNotifier(RabbitTemplate rabbitTemplate) {
        return new RabbitPersonNotifier(rabbitTemplate);
    }
}
