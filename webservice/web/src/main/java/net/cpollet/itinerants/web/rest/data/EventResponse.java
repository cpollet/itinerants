package net.cpollet.itinerants.web.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cpollet.itinerants.core.domain.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 11.02.17.
 */
@Data
public class EventResponse {
    private final String eventId;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime dateTime;
    private final List<PersonResponse> availablePeople;
    private final List<PersonResponse> attendingPeople;

    public EventResponse(String eventId, String name, LocalDateTime date, List<PersonResponse> availablePeople, List<PersonResponse> attendingPeople) {
        this.eventId = eventId;
        this.name = name;
        this.dateTime = date;
        this.availablePeople = availablePeople;
        this.attendingPeople = attendingPeople;
    }

    public EventResponse(Event event) {
        this(
                event.id(),
                event.name(),
                event.dateTime(),
                event.availablePeople().stream()
                        .map(p -> new PersonResponse(p.id(), p.firstName()))
                        .collect(Collectors.toList()),
                event.attendingPeople().stream()
                        .map(p -> new PersonResponse(p.id(), p.firstName()))
                        .collect(Collectors.toList())
        );
    }
}

