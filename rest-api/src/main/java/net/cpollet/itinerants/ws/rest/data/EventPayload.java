package net.cpollet.itinerants.ws.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class EventPayload {
    private final Long eventId;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime dateTime;
}
