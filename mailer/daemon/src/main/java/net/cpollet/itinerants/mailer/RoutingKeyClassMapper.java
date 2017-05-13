package net.cpollet.itinerants.mailer;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cpollet on 13.05.17.
 */
public class RoutingKeyClassMapper implements ClassMapper {
    private final Map<String, Class<?>> mapping ;

    public RoutingKeyClassMapper() {
        this.mapping = new HashMap<>();
    }

    @Override
    public void fromClass(Class<?> clazz, MessageProperties properties) {
        // nothing
    }

    @Override
    public Class<?> toClass(MessageProperties properties) {
        return mapping.get(properties.getReceivedRoutingKey());
    }

    public void register(String routingKey, Class<NewAccountMessage> clazz) {
        mapping.put(routingKey, clazz);
    }
}
