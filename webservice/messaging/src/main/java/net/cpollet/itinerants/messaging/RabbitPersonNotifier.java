package net.cpollet.itinerants.messaging;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.mailer.NewAccountMessage;
import net.cpollet.itinerants.mailer.ResetPasswordMessage;
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
    public void notifyNewAccount(Person person, PasswordResetData passwordResetData) {
        log.info("Notify new account for username '{}'", person.username());
        rabbitTemplate.convertAndSend(new NewAccountMessage(person.email(), passwordResetData.token(), person.username()));
    }

    @Override
    public void notifyResetPassword(Person person, PasswordResetData passwordResetData) {
        log.info("Notify reset password for username '{}'", person.username());
        rabbitTemplate.convertAndSend(new ResetPasswordMessage(person.email(), passwordResetData.token(), person.username()));
    }
}
