package net.cpollet.itinerants.core.algorithm;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 06.04.17.
 */
@Slf4j
public class SimpleAttendeeSelection implements AttendeeSelection {
    private final List<Event> events;
    private final Map<Event, Set<Person>> availabilities;
    private final Map<Person, Integer> pastAttendancesCount;
    private final Map<Event, Set<Person>> initialAttendances;
    private final int pastEventsCount;

    public SimpleAttendeeSelection(Parameters parameters) {
        this.availabilities = parameters.getAvailabilities();
        this.pastEventsCount = parameters.getPastEventsCount();
        this.pastAttendancesCount = parameters.getPastAttendancesCount();
        this.initialAttendances = parameters.getInitialAttendances();
        this.events = availabilities.keySet().stream()
                .sorted((e1, e2) -> e1.dateTime().compareTo(e2.dateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Event, Set<Person>> selection() {
        if (events.isEmpty()) {
            return Collections.emptyMap();
        }

        State state = new State(pastEventsCount + events.size(), new HashMap<>(0), new HashMap<>(0));

        for (Map.Entry<Person, Integer> personAttendance : pastAttendancesCount.entrySet()) {
            state = state.previousAttendances(personAttendance.getKey(), personAttendance.getValue());
        }

        // TODO TDD This...
//        for (Map.Entry<Event, Set<Person>> eventAttendance : initialAttendances.entrySet()) {
//            for (Person person : eventAttendance.getValue()) {
//                state = state.select(person, eventAttendance.getKey());
//            }
//        }

        for (Event event : events) {
            state = state.withEvent(event);
            while (state.attendees(event).size() < event.attendeesCount()) {
                try {
                    Person person = state.find(event, availabilities.getOrDefault(event, Collections.emptySet()));
                    state = state.select(person, event);
                } catch (Exception e) {
                    log.info("unable to find enough attendee for {} (found {})", event.id(), state.attendees(event).size(), e);
                    break;
                }
            }
        }

        return state.attendances();
    }

    private class State {
        private final int eventsCount;
        private final Map<Person, Integer> attendancesCount;
        private final Map<Event, Set<Person>> attendances;
        private final State parent;

        private State(int eventsCount, Map<Person, Integer> attendancesCount, Map<Event, Set<Person>> attendances, State parent) {
            this.eventsCount = eventsCount;
            this.attendancesCount = Collections.unmodifiableMap(attendancesCount);
            this.attendances = Collections.unmodifiableMap(attendances);
            this.parent = parent;
        }

        private State(int eventsCount, Map<Person, Integer> attendancesCount, Map<Event, Set<Person>> attendances) {
            this(eventsCount, attendancesCount, attendances, null);
        }

        State previousAttendances(Person person, int count) {
            Map<Person, Integer> attendancesCount = new HashMap<>(this.attendancesCount);
            attendancesCount.put(person, attendancesCount.getOrDefault(person, 0) + count);

            return new State(eventsCount, attendancesCount, attendances, this);
        }

        State select(Person person, Event event) {
            Map<Person, Integer> attendancesCount = new HashMap<>(this.attendancesCount);
            attendancesCount.put(person, attendancesCount.getOrDefault(person, 0) + 1);

            Map<Event, Set<Person>> attendances = new HashMap<>(this.attendances);
            attendances.put(event, mergeSet(attendances.getOrDefault(event, Collections.emptySet()), person));

            return new State(eventsCount, attendancesCount, attendances, this);
        }

        private <T> Set<T> mergeSet(Set<T> set, T element) {
            Set<T> newSet = new HashSet<>(set);
            newSet.add(element);
            return newSet;
        }

        Person find(Event event, Set<Person> availabilities) {
            return availabilities.stream()
                    .filter(p -> !alreadySelected(p, event))
                    .sorted(this::compareScore)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No more available person"));
        }

        Set<Person> attendees(Event event) {
            return attendances.getOrDefault(event, Collections.emptySet());
        }

        Map<Event, Set<Person>> attendances() {
            return attendances;
        }

        State withEvent(Event event) {
            Map<Event, Set<Person>> attendances = new HashMap<>(this.attendances);
            attendances.put(event, Collections.emptySet());
            return new State(eventsCount, attendancesCount, attendances, this);
        }

        private boolean alreadySelected(Person person, Event event) {
            return attendances.getOrDefault(event, Collections.emptySet()).contains(person);
        }

        private int compareScore(Person p1, Person p2) {
            return (int) Math.signum(score(p1) - score(p2));
        }

        private float score(Person person) {
            float ratio = (float) attendancesCount.getOrDefault(person, 0) / eventsCount;
            return ratio / person.targetRatio();
        }
    }
}
