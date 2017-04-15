package net.cpollet.itinerants.web.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cpollet.itinerants.core.algorithm.Attendee;
import net.cpollet.itinerants.core.domain.Event;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 14.04.17.
 */
@Data
public class AttendanceResponse {
    private final String eventId;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime dateTime;
    private final Set<AttendeeResponse> selectedPeople;
    private final Set<AttendeeResponse> availablePeople;

    public AttendanceResponse(Event event, Set<Attendee> attendees, Set<Attendee> availablePeople) {
        eventId = event.id();
        name = event.name();
        dateTime = event.dateTime();
        this.selectedPeople = attendees.stream()
                .map(a -> new AttendeeResponse(a.getPerson().id(), a.getPerson().name()))
                .collect(Collectors.toSet());
        this.availablePeople = availablePeople.stream()
                .map(a -> new AttendeeResponse(a.getPerson().id(), a.getPerson().name()))
                .collect(Collectors.toSet());
    }

    @Data
    private class AttendeeResponse {
        private final String personId;
        private final String name;

        private AttendeeResponse(String personId, String name) {
            this.personId = personId;
            this.name = name;
        }
    }
}
