package net.cpollet.itinerants.ws.service.data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
public interface Event {
    Long getId();

    String getName();

    LocalDateTime getDateTime();

    Set<? extends Person> availablePeople();
}
