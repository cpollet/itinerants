package net.cpollet.itinerants.mailer.context;

import net.cpollet.itinerants.mailer.NewAccountMessage;
import net.cpollet.itinerants.mailer.RoutingKeyClassMapper;
import net.cpollet.itinerants.mailer.handlers.NewAccountHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;

/**
 * Created by cpollet on 13.05.17.
 */
@Configuration
public class NewAccountMessagingContext {
    private static final String QUEUE_NAME = "mailer-daemon.account-events.";
    private static final String NEW_ACCOUNT_EVENT_TAG = "new";

    @Autowired
    private RoutingKeyClassMapper classMapper;

    @SuppressWarnings("unused")
    @PostConstruct
    public void registerClassMapper() {
        classMapper.register(NEW_ACCOUNT_EVENT_TAG, NewAccountMessage.class);
    }

    @Bean
    String queueNameForNewAccount() {
        return QUEUE_NAME + NEW_ACCOUNT_EVENT_TAG;
    }

    @Bean
    Queue queueForNewAccount(String queueNameForNewAccount) {
        return new Queue(queueNameForNewAccount, false);
    }

    @Bean
    Binding bindingForNewAccount(Queue queueForNewAccount, DirectExchange exchange) {
        return BindingBuilder
                .bind(queueForNewAccount)
                .to(exchange)
                .with(NEW_ACCOUNT_EVENT_TAG);
    }

    @Bean
    SimpleMessageListenerContainer containerForNewAccount(ConnectionFactory connectionFactory,
                                                          NewAccountHandler receiver,
                                                          String queueNameForNewAccount,
                                                          MessageConverter jsonMessageConverter,
                                                          ConsumerTagStrategy consumerTagStrategy) throws UnknownHostException {

        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver, jsonMessageConverter);
        messageListener.setDefaultListenerMethod("handle");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(messageListener);
        container.setQueueNames(queueNameForNewAccount);
        container.setConsumerTagStrategy(consumerTagStrategy);

        return container;
    }
}
