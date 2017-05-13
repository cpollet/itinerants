package net.cpollet.itinerants.mailer.context;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Created by cpollet on 12.05.17.
 */
@Configuration
@Slf4j
public class MessagingContext {
    @Bean
    String exchangeName() {
        return "account-events.fx"; // TODO move to properties
    }

    @Bean
    String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.warn("Could not determine hostname");
            return "unknown";
        }
    }

    @Bean
    ConnectionNameStrategy connectionNameStrategy(String hostname, String applicationName) {
        return connectionFactory -> hostname + "." + applicationName + "." + UUID.randomUUID().toString();
    }

    @SuppressWarnings("Duplicates")
    @Bean
    ConnectionFactory connectionFactory(ConnectionNameStrategy connectionNameStrategy,
                                        RabbitProperties rabbitProperties) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost(), rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());

        connectionFactory.setConnectionNameStrategy(connectionNameStrategy);

        return connectionFactory;
    }

    @Bean
    FanoutExchange exchange(String exchangeName) {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    ConsumerTagStrategy consumerTagStrategy(String hostname, String applicationName) {
        return queue -> hostname + "." + applicationName + "." + UUID.randomUUID().toString();
    }

    @Bean
    String queueName(String applicationName, String exchangeName) {
        return exchangeName + " > " + applicationName;
    }

    @Bean
    Queue queue(String queueName) {
        return new Queue(queueName, false);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             Receiver receiver,
                                             String queueName,
                                             ConsumerTagStrategy consumerTagStrategy) throws UnknownHostException {

        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver, new Jackson2JsonMessageConverter());
        messageListener.setDefaultListenerMethod("handle");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(messageListener);
        container.setQueueNames(queueName);
        container.setConsumerTagStrategy(consumerTagStrategy);

        return container;
    }
}
