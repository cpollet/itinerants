package net.cpollet.itinerants.web.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cpollet.itinerants.core.algorithm.Attendee;
import net.cpollet.itinerants.core.domain.Event;

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

    public AttendanceListResponse(Map<Event, Set<Attendee>> selection, Map<Event, Set<Attendee>> input, int pastEventsCount) {
        this.pastEventsCount = pastEventsCount;
        events = selection.entrySet().stream()
                .map(e -> new AttendanceResponse(e.getKey(), e.getValue(), input.get(e.getKey())))
                .collect(Collectors.toList());
        attendees = input.values().stream()
                .flatMap(Set::stream)
                .distinct()
                .map(e -> new AttendeeResponse(e.id(), e.name(), e.targetRatio()))
                .collect(Collectors.toMap(AttendeeResponse::getPersonId, e -> e));
    }

    @Data
    private class AttendeeResponse {
        private final String personId;
        private final String name;
        private final int pastAttendancesCount;
        private final float targetRatio;

        private AttendeeResponse(String personId, String name, float targetRatio) {
            this.personId = personId;
            this.name = name;
            this.pastAttendancesCount = 0;// FIXME this should come from existing (past) attendances
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

        private AttendanceResponse(Event event, Set<Attendee> attendees, Set<Attendee> availablePeople) {
            eventId = event.id();
            name = event.name();
            eventSize = event.size();
            dateTime = event.dateTime();
            this.selectedPeople = attendees.stream()
                    .map(a -> a.getPerson().id())
                    .collect(Collectors.toSet());
            this.availablePeople = availablePeople.stream()
                    .map(a -> a.getPerson().id())
                    .collect(Collectors.toSet());
        }
    }
}
