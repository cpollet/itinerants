package net.cpollet.itinerants.ws.api.v1.data;

import lombok.Data;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class AvailabilityResponse {
    private final PersonResponse person;
    private final EventResponse event;
}
