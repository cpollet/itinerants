package net.cpollet.itinerants.core.algorithm;

import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;

import java.util.Map;
import java.util.Set;

/**
 * Created by cpollet on 12.04.17.
 */
public interface AttendeeSelection {
    Map<Event, Set<Person>> selection();

    interface Factory {
        AttendeeSelection create(int pastEventsCount,
                                 Map<Person, Long> pastAttendancesCount,
                                 Map<Event, Set<Person>> availabilities,
                                 Map<Event, Set<Person>> initialAttendances);
    }
}
