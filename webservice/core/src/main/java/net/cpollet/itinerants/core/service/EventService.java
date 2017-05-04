package net.cpollet.itinerants.core.service;

import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.domain.data.PersonData;

import java.util.List;
import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventService {
    EventData getById(String id);

    List<Event> getByIds(List<String> ids);

    String create(EventData eventData);

    List<EventData> future(SortOrder sortOrder);

    List<EventData> future(String username, SortOrder sortOrder);

    List<EventData> past(SortOrder sortOrder);

    enum SortOrder {
        ASCENDING, DESCENDING
    }

    abstract class InputEventData implements EventData {
        @Override
        public String getUUID() {
            throw new IllegalStateException();
        }

        @Override
        public Set<? extends PersonData> availablePeople() {
            throw new IllegalStateException();
        }

        @Override
        public Set<? extends PersonData> attendingPeople() {
            throw new IllegalStateException();
        }

        @Override
        public Integer getAttendeesCount() {
            throw new IllegalStateException();
        }
    }
}
