package net.cpollet.itinerants.messaging.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private static final String EXCHANGE_NAME = "account-events.fx"; // TODO move to properties

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
    ConnectionNameStrategy connectionNameStrategy(String hostname, String applicationName) {
        return connectionFactory -> hostname + "." + applicationName + "." + UUID.randomUUID().toString();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
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
}
