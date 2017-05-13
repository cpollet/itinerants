package net.cpollet.itinerants.mailer.emails;

import java.util.Map;

/**
 * Created by cpollet on 13.05.17.
 */
public interface Email {
    String content(Map<String, Object> variables) throws EmailException;

    String subject() throws EmailException;
}
