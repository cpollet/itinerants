package net.cpollet.itinerants.ws.service;


import net.cpollet.itinerants.ws.domain.data.EventData;
import net.cpollet.itinerants.ws.domain.data.PersonData;

import java.util.List;
import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventService {
    EventData getById(long id);

    long create(EventData eventData);

    List<EventData> future(SortOrder sortOrder);

    List<EventData> past(SortOrder sortOrder);

    enum SortOrder {
        ASCENDING, DESCENDING
    }

    abstract class InputEventData implements EventData {
        @Override
        public Long getId() {
            throw new IllegalStateException();
        }

        @Override
        public Set<? extends PersonData> availablePeople() {
            throw new IllegalStateException();
        }
    }
}
