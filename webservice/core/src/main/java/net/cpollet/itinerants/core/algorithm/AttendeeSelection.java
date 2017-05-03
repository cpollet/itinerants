package net.cpollet.itinerants.core.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
        AttendeeSelection create(Parameters parameters);
    }

    @AllArgsConstructor
    @Getter
    class Parameters {
        private final int pastEventsCount;
        private final Map<Person, Integer> pastAttendancesCount;
        private final Map<Event, Set<Person>> availabilities;
        private final Map<Event, Set<Person>> initialAttendances;
    }
}
