package net.cpollet.itinerants.core.domain;

import net.cpollet.itinerants.core.domain.data.EventData;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 14.04.17.
 */
public class Event {
    private final EventData eventData;
    private final Person.Factory personFactory;

    public Event(EventData eventData, Person.Factory personFactory) {
        this.eventData = eventData;
        this.personFactory = personFactory;
    }

    public String id() {
        return eventData.getUUID();
    }

    public String name() {
        return eventData.getName();
    }

    public Set<Person> availablePeople() {
        return eventData.availablePeople().stream()
                .map(personFactory::create)
                .collect(Collectors.toSet());
    }

    public Integer attendeesCount() {
        return eventData.getAttendeesCount();
    }
}
