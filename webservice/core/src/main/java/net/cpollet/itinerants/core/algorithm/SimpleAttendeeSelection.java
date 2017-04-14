package net.cpollet.itinerants.core.algorithm;

import net.cpollet.itinerants.core.domain.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 06.04.17.
 */
public class SimpleAttendeeSelection implements AttendeeSelection {
    private final Set<Event> events;
    private final Map<Event, Set<Attendee>> availabilities;
    private final Map<Attendee, Attendee> map;
    private final int eventsCount;

    public SimpleAttendeeSelection(Map<Event, Set<Attendee>> availabilities, int pastEventsCount) {
        this.availabilities = Collections.unmodifiableMap(availabilities);
        this.events = Collections.unmodifiableSet(availabilities.keySet());
        this.map = availabilities.values().stream()
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toMap(p -> p, p -> p));
        this.eventsCount = pastEventsCount + availabilities.size();
    }

    @Override
    public Map<Event, Set<Attendee>> selection() {
        if (events.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Event, Set<Attendee>> result = new HashMap<>(events.size());

        for (Event event : events) {
            if (!availabilities.containsKey(event)) {
                result.put(event, Collections.emptySet());
            } else {
                Set<Attendee> attendees = selectAttendees(event);
                updateAttendances(attendees);

                result.put(event, attendees);
            }
        }

        return result;
    }

    private void updateAttendances(Set<Attendee> attendees) {
        attendees.forEach(p -> map.put(p, map.get(p).withIncreasedCount()));
    }

    private Set<Attendee> selectAttendees(Event event) {
        return availabilities.get(event).stream()
                .sorted(this::compareScore)
                .limit(event.attendeesCount())
                .collect(Collectors.toSet());
    }

    private int compareScore(Attendee p1, Attendee p2) {
        float p1AttendanceRateToTargetRatio = attendanceRate(p1) / p1.targetRatio();
        float p2AttendanceRateToTargetRatio = attendanceRate(p2) / p2.targetRatio();

        return (int) Math.signum(p1AttendanceRateToTargetRatio - p2AttendanceRateToTargetRatio);
    }

    private float attendanceRate(Attendee attendee) {
        return (float) map.get(attendee).getParticipationCount() / eventsCount;
    }
}
