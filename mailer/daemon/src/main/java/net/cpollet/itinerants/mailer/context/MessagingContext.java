package net.cpollet.itinerants.mailer.context;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.JsonConverter;
import net.cpollet.itinerants.mailer.RoutingKeyClassMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.MessageConverter;
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
    private static final String EXCHANGE_NAME = "account-events.dx";

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
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
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
    ConsumerTagStrategy consumerTagStrategy(String hostname, String applicationName) {
        return queue -> hostname + "." + applicationName + "." + UUID.randomUUID().toString();
    }

    @Bean
    RoutingKeyClassMapper classMapper() {
        return new RoutingKeyClassMapper();
    }

    @Bean
    MessageConverter jsonMessageConverter(RoutingKeyClassMapper classMapper) {
        return new JsonConverter(classMapper);
    }

}
