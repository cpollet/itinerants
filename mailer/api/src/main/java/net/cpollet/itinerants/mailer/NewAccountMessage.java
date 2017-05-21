package net.cpollet.itinerants.mailer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by cpollet on 11.05.17.
 */
@AllArgsConstructor
@ToString
@Getter
public class NewAccountMessage {
    private final String email;
    private final String token;
    private final String username;
}
