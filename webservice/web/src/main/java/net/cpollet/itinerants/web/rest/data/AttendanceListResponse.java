package net.cpollet.itinerants.web.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 18.04.17.
 */
@Data
public class AttendanceListResponse {
    private final List<AttendanceResponse> events;
    private final Map<String, AttendeeResponse> attendees;
    private final int pastEventsCount;

    public AttendanceListResponse(Map<Event, Set<Person>> selection,
                                  Map<Event, Set<Person>> availabilities,
                                  Map<Person, Long> pastAttendancesCount,
                                  int pastEventsCount) {
        this.pastEventsCount = pastEventsCount;

        final Map<String, Long> pastAttendancesCountByPersonId = pastAttendancesCount.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().id(), Map.Entry::getValue));

        events = selection.entrySet().stream()
                .map(e -> new AttendanceResponse(e.getKey(), e.getValue(), availabilities.get(e.getKey())))
                .collect(Collectors.toList());

        attendees = availabilities.values().stream()
                .flatMap(Set::stream)
                .distinct()
                .map(e -> new AttendeeResponse(
                        e.id(),
                        e.firstName(),
                        e.targetRatio(),
                        pastAttendancesCountByPersonId.getOrDefault(e.id(), 0L))
                )
                .collect(Collectors.toMap(AttendeeResponse::getPersonId, e -> e));
    }

    @Data
    private class AttendeeResponse {
        private final String personId;
        private final String name;
        private final long pastAttendancesCount;
        private final float targetRatio;

        private AttendeeResponse(String personId, String name, float targetRatio, Long pastAttendancesCount) {
            this.personId = personId;
            this.name = name;
            this.pastAttendancesCount = pastAttendancesCount;
            this.targetRatio = targetRatio;
        }
    }

    @Data
    private class AttendanceResponse {
        private final String eventId;
        private final String name;
        private final Integer eventSize;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime dateTime;
        private final Set<String> selectedPeople;
        private final Set<String> availablePeople;

        private AttendanceResponse(Event event, Set<Person> attendees, Set<Person> availablePeople) {
            eventId = event.id();
            name = event.name();
            eventSize = event.size();
            dateTime = event.dateTime();
            this.selectedPeople = attendees.stream()
                    .map(Person::id)
                    .collect(Collectors.toSet());
            this.availablePeople = availablePeople.stream()
                    .map(Person::id)
                    .collect(Collectors.toSet());
        }
    }
}
