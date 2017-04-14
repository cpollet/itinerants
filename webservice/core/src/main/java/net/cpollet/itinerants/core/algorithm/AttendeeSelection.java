package net.cpollet.itinerants.core.algorithm;

import net.cpollet.itinerants.core.domain.Event;

import java.util.Map;
import java.util.Set;

/**
 * Created by cpollet on 12.04.17.
 */
public interface AttendeeSelection {
    Map<Event, Set<Attendee>> selection();
}
