package net.cpollet.itinerants.core.algorithm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;

import java.util.Collections;
import java.util.Comparator;
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
@AllArgsConstructor
public class SimpleAttendeeSelection implements AttendeeSelection {
    private final int pastEventsCount;
    private final Map<Person, Integer> pastAttendancesCount;
    private final Map<Event, Set<Person>> availabilities;
    private final Map<Event, Set<Person>> initialAttendances;

    @Override
    public Map<Event, Set<Person>> selection() {
        List<Event> events = availabilities.keySet().stream()
                .sorted(Comparator.comparing(Event::dateTime))
                .filter(e -> e.attendeesCount() != null)
                .collect(Collectors.toList());

        if (events.isEmpty()) {
            return Collections.emptyMap();
        }

        State state = State.create(pastEventsCount, events);

        for (Map.Entry<Person, Integer> personAttendance : pastAttendancesCount.entrySet()) {
            state.addPreviousAttendances(personAttendance.getKey(), personAttendance.getValue());
        }

        for (Map.Entry<Event, Set<Person>> eventAttendance : initialAttendances.entrySet()) {
            for (Person person : eventAttendance.getValue()) {
                state.select(person, eventAttendance.getKey());
            }
        }

        for (Event event : events) {
            while (state.attendees(event).size() < event.attendeesCount()) {
                try {
                    Person person = state.find(event, availabilities.getOrDefault(event, Collections.emptySet()));
                    state.select(person, event);
                } catch (Exception e) {
                    log.info("unable to find enough attendee for {} (found {})", event.id(), state.attendees(event).size(), e);
                    break;
                }
            }
        }

        return state.attendances();
    }

    private static class State {
        private final int eventsCount;
        private final Map<Person, Integer> attendancesCount;
        private final Map<Event, Set<Person>> attendances;

        private State(int eventsCount, Map<Person, Integer> attendancesCount, Map<Event, Set<Person>> attendances) {
            this.eventsCount = eventsCount;
            this.attendancesCount = attendancesCount;
            this.attendances = attendances;
        }

        public static State create(int pastEventsCount, List<Event> events) {
            Map<Event, Set<Person>> attendances = new HashMap<>(events.size());
            events.forEach(e -> attendances.put(e, new HashSet<>()));

            return new State(pastEventsCount + events.size(), new HashMap<>(), attendances);
        }

        void addPreviousAttendances(Person person, int count) {
            attendancesCount.put(person, attendancesCount.getOrDefault(person, 0) + count);
        }

        void select(Person person, Event event) {
            attendancesCount.put(person, attendancesCount.getOrDefault(person, 0) + 1);

            attendances.put(event, mergeSet(attendances.getOrDefault(event, Collections.emptySet()), person));
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
            return attendances.get(event);
        }

        Map<Event, Set<Person>> attendances() {
            return attendances;
        }

        private boolean alreadySelected(Person person, Event event) {
            return attendances.get(event).contains(person);
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
