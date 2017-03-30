package net.cpollet.itinerants.ws.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cpollet.itinerants.ws.domain.data.EventData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 11.02.17.
 */
@Data
public class EventResponse {
    private final Long eventId;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime dateTime;
    private final List<PersonResponse> availablePeople;

    public EventResponse(Long eventId, String name, LocalDateTime date, List<PersonResponse> availablePeople) {
        this.eventId = eventId;
        this.name = name;
        this.dateTime = date;
        this.availablePeople = availablePeople;
    }

    public EventResponse(EventData eventData) {
        this(
                eventData.getId(),
                eventData.getName(),
                eventData.getDateTime(),
                eventData.availablePeople().stream()
                        .map(p -> new PersonResponse(p.getId(), p.getName()))
                        .collect(Collectors.toList())
        );
    }
}

