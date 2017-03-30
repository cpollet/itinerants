package net.cpollet.itinerants.ws.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
public class AvailabilityResponse {
    private final PersonResponse person;
    private final EventResponse event;
}
