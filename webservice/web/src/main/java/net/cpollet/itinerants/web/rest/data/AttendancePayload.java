package net.cpollet.itinerants.web.rest.data;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by cpollet on 01.05.17.
 */
@Data
@ToString
public class AttendancePayload {
    private final String eventId;
    private final List<String> personIds;
}
