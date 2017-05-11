package net.cpollet.itinerants.mailer;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.mailer.handlers.Handler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cpollet on 11.05.17.
 */
@Component
@Slf4j
public class Receiver {
    private List<Handler> handlers;

    public void handleMessage(Notification message) {
        log.info("received {}", message);
    }
}
