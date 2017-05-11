package net.cpollet.itinerants.mailer.context;

import net.cpollet.itinerants.mailer.Notification;
import net.cpollet.itinerants.mailer.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cpollet on 11.05.17.
 */
@Configuration
public class ApplicationContext {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 5672;
    private static final String QUEUE_NAME = "spring-boot";
    private static final String EXCHANGE_NAME = "spring-boot-exchange";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(HOSTNAME, PORT);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver, MessageConverter messageConverter) {
        return new MessageListenerAdapter(receiver, messageConverter);
    }

    @Bean
    MessageConverter messageConverter() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(Notification.class);

        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper);

        return messageConverter;
    }

}
