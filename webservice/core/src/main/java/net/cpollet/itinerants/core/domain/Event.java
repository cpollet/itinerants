package net.cpollet.itinerants.core.domain;

import net.cpollet.itinerants.core.domain.data.EventData;

import java.time.LocalDateTime;
import java.util.Objects;
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

    public Set<Person> attendingPeople() {
        return eventData.attendingPeople().stream()
                .map(personFactory::create)
                .collect(Collectors.toSet());
    }

    public Integer attendeesCount() {
        return eventData.getAttendeesCount();
    }

    public LocalDateTime dateTime() {
        return eventData.getDateTime();
    }

    public Integer size() {
        return eventData.getAttendeesCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventData.getUUID(), event.eventData.getUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventData.getUUID());
    }

    @Override
    public String toString() {
        return "Event[" + eventData.getUUID() + ": " + eventData.getName() + " - " + eventData.getDateTime() + "]";
    }
}
