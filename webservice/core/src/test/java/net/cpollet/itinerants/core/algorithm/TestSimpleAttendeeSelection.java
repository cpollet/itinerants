package net.cpollet.itinerants.core.algorithm;

import com.google.common.collect.ImmutableMap;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by cpollet on 06.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSimpleAttendeeSelection {
    @Mock
    private Person person1;
    @Mock
    private Person person2;
    @Mock
    private Person person3;
    @Mock
    private Event event1;
    @Mock
    private Event event2;
    @Mock
    private Event event3;

    @Test
    public void compute_returnsEmptyMap_whenThereAreNoEvents() {
        // GIVEN
        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(Collections.emptyMap(), 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void compute_returnsFilledMap_whenThereIsOneEvent() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(1);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Collections.emptyList())
                .build();
        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
    }

    @Test
    public void compute_returnsFilledMapWithPerson_whenThereIsOneEventAndOnePerson() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(1);
        Attendee attendee = new Attendee(person1, 0);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Collections.singletonList(attendee))
                .build();
        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        assertThat(result).containsKey(event1);
        assertThat(result.get(event1)).hasSize(1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(attendee);
    }

    @Test
    public void compute_returnsEmptyMap_whenThereIsOneEventAndOnePersonForAnotherEvent() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(1);
        Mockito.when(event2.attendeesCount()).thenReturn(1);
        Attendee attendee = new Attendee(person1, 0);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Collections.emptyList())
                .put(event2, Collections.singletonList(attendee))
                .build();
        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        assertThat(result).containsKey(event1);
        assertThat(result.get(event1)).hasSize(0);
    }

    @Test
    public void compute_returnsFilledMapWithoutTooManyPeople_whenThereIsOneEventAndManyPeople() {
        // GIVEN
        Mockito.when(person1.targetRatio()).thenReturn(1f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when((event1.attendeesCount())).thenReturn(1);
        Attendee attendee1 = new Attendee(person1, 0);
        Attendee attendee2 = new Attendee(person2, 0);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Arrays.asList(attendee1, attendee2))
                .build();
        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        assertThat(result.get(event1)).hasSize(1);
    }

    @Test
    public void compute_returnsFilledMapWithLessPresentPerson_whenThereIsOneEventAndManyPeople() {
        // GIVEN
        Mockito.when(person1.targetRatio()).thenReturn(1f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when(event1.attendeesCount()).thenReturn(1);

        Attendee attendee1 = new Attendee(person1, 1);
        Attendee attendee2 = new Attendee(person2, 0);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Arrays.asList(attendee1, attendee2))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(attendee2);
    }

    @Test
    public void compute_returnsFilledMapWithLessPresentPeople_whenThereIsSeveralEventAndManyPeople() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(2);
        Mockito.when(event2.attendeesCount()).thenReturn(2);
        Mockito.when(event3.attendeesCount()).thenReturn(1);
        Mockito.when(person1.targetRatio()).thenReturn(1f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when(person3.targetRatio()).thenReturn(1f);

        Attendee attendee1 = new Attendee(person1, 0);
        Attendee attendee2 = new Attendee(person2, 0);
        Attendee attendee3 = new Attendee(person3, 1);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Arrays.asList(attendee1, attendee2))
                .put(event2, Arrays.asList(attendee1, attendee2))
                .put(event3, Arrays.asList(attendee1, attendee2, attendee3))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 0);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(attendee1, attendee2);

        assertThat(result).containsKey(event2);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event2)).containsExactly(attendee1, attendee2);

        assertThat(result).containsKey(event2);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event3)).containsExactly(attendee3);
    }

    @Test
    public void compute_returnsFilledMapWithPreferredPresentPeople_whenThereIsOneEventAndManyPeople() {
        // GIVEN
        Mockito.when(person1.targetRatio()).thenReturn(0.25f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when(event1.attendeesCount()).thenReturn(1);

        Attendee attendee1 = new Attendee(person1, 1);
        Attendee attendee2 = new Attendee(person2, 2);
        ImmutableMap<Event, List<Attendee>> availabilities = ImmutableMap.<Event, List<Attendee>>builder()
                .put(event1, Arrays.asList(attendee1, attendee2))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(availabilities, 2);

        // WHEN
        Map<Event, Set<Attendee>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(attendee2);
    }
}
