package net.cpollet.itinerants.core.domain.data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventData {
    String getUUID();

    String getName();

    LocalDateTime getDateTime();

    Set<? extends PersonData> availablePeople();
}
