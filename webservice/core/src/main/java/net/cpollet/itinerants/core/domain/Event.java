package net.cpollet.itinerants.core.domain;

import net.cpollet.itinerants.core.domain.data.EventData;

/**
 * Created by cpollet on 14.04.17.
 */
public class Event {
    private final EventData eventData;

    public Event(EventData eventData) {
        this.eventData = eventData;
    }

    public Integer attendeesCount() {
        return eventData.getAttendeesCount();
    }
}
