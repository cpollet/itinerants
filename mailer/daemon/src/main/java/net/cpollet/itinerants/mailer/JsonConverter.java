package net.cpollet.itinerants.mailer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.IOException;

/**
 * Created by cpollet on 13.05.17.
 */
public class JsonConverter implements MessageConverter {
    private static final String ENCODING = "UTF-8";

    private final ClassMapper classMapper;
    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    public JsonConverter(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Class<?> targetClass = classMapper.toClass(
                message.getMessageProperties());

        try {
            return convertBytesToObject(message.getBody(), targetClass);
        } catch (IOException e) {
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        }
}

    private Object convertBytesToObject(byte[] body, Class<?> targetClass) throws IOException {
        String contentAsString = new String(body, ENCODING);
        return jsonObjectMapper.readValue(contentAsString, jsonObjectMapper.constructType(targetClass));
    }
}
