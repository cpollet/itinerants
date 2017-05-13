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
    Event getById(String id);

    List<Event> getByIds(List<String> ids);

    String create(EventData eventData);

    List<Event> future(SortOrder sortOrder);

    List<Event> future(String username, SortOrder sortOrder);

    List<Event> past(SortOrder sortOrder);

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
