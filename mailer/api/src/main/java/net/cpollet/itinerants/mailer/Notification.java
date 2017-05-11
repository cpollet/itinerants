package net.cpollet.itinerants.mailer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Created by cpollet on 11.05.17.
 */
@AllArgsConstructor
@ToString
@Getter
public class Notification {
    private final String email;
    private final String type;
    private Map<String,String> properties;
}
