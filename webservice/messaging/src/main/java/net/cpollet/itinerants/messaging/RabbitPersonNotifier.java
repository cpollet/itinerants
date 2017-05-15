package net.cpollet.itinerants.messaging;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.mailer.NewAccountMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by cpollet on 13.05.17.
 */
@Slf4j
public class RabbitPersonNotifier implements Person.Notifier {
    private final RabbitTemplate rabbitTemplate;

    public RabbitPersonNotifier(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyNewAccount(Person person, NewAccountData newAccountData) {
        log.info("Notify new account for username '{}'", person.username());
        rabbitTemplate.convertAndSend(new NewAccountMessage(person.email(), newAccountData.token(), person.username()));
    }
}
