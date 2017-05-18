package net.cpollet.itinerants.core.algorithm;

import com.google.common.collect.ImmutableMap;
import com.google.common.truth.Truth;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

    @Before
    public void setup() {
        Mockito.when(event1.id()).thenReturn("e1");
        Mockito.when(event1.dateTime()).thenReturn(LocalDateTime.now().plusDays(1));
        Mockito.when(event2.dateTime()).thenReturn(LocalDateTime.now().plusDays(2));
        Mockito.when(event3.dateTime()).thenReturn(LocalDateTime.now().plusDays(3));
    }

    @Test
    public void compute_returnsEmptyMap_whenThereAreNoEvents() {
        // GIVEN
        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void compute_returnsFilledMap_whenThereIsOneEvent() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(1);
        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, Collections.emptySet())
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                Collections.emptyMap(),
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
    }

    @Test
    public void compute_returnsFilledMapWithPerson_whenThereIsOneEventAndOnePerson() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(1);
        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, Collections.singleton(person1))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                Collections.emptyMap(),
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        assertThat(result).containsKey(event1);
        assertThat(result.get(event1)).hasSize(1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(person1);
    }

    @Test
    public void compute_returnsEmptyMap_whenThereIsOneEventAndOnePersonForAnotherEvent() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(1);
        Mockito.when(event2.attendeesCount()).thenReturn(1);
        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, Collections.emptySet())
                .put(event2, Collections.singleton(person1))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                Collections.emptyMap(),
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        assertThat(result).containsKey(event1);
        assertThat(result.get(event1)).hasSize(0);
    }

    @Test
    public void compute_returnsFilledMapWithoutTooManyPeople_whenThereIsOneEventAndManyPeople() {
        // GIVEN
        Mockito.when(person1.targetRatio()).thenReturn(1f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when((event1.attendeesCount())).thenReturn(1);
        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, new HashSet<>(Arrays.asList(person1, person2)))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                Collections.emptyMap(),
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

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

        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, new HashSet<>(Arrays.asList(person1, person2)))
                .build();

        ImmutableMap<Person, Integer> pastAttendancesCount = ImmutableMap.<Person, Integer>builder()
                .put(person1, 1)
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                pastAttendancesCount,
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(person2);
    }

    @Test
    public void compute_returnsFilledMapWithLessPresentPeople_whenThereIsSeveralEventAndManyPeople() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(2);
        Mockito.when(event2.attendeesCount()).thenReturn(2);
        Mockito.when(event3.attendeesCount()).thenReturn(2);
        Mockito.when(person1.targetRatio()).thenReturn(1f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when(person3.targetRatio()).thenReturn(1f);

        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, new HashSet<>(Arrays.asList(person1, person2)))
                .put(event2, new HashSet<>(Arrays.asList(person1, person2)))
                .put(event3, new HashSet<>(Arrays.asList(person1, person2, person3)))
                .build();

        ImmutableMap<Person, Integer> pastAttendancesCount = ImmutableMap.<Person, Integer>builder()
                .put(person2, 1)
                .put(person3, 1)
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                1,
                pastAttendancesCount,
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(person1, person2);

        assertThat(result).containsKey(event2);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event2)).containsExactly(person1, person2);

        assertThat(result).containsKey(event2);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event3)).containsExactly(person1, person3);
    }

    @Test
    public void compute_returnsFilledMapWithPreferredPresentPeople_whenThereIsOneEventAndManyPeople() {
        // GIVEN
        Mockito.when(person1.targetRatio()).thenReturn(0.25f);
        Mockito.when(person2.targetRatio()).thenReturn(1f);
        Mockito.when(event1.attendeesCount()).thenReturn(1);

        ImmutableMap<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, new HashSet<>(Arrays.asList(person1, person2)))
                .build();

        Map<Person, Integer> pastAttendancesCount = new HashMap<>();
        pastAttendancesCount.put(person1, 1);
        pastAttendancesCount.put(person2, 2);

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                2,
                pastAttendancesCount,
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(person2);
    }

    @Test
    public void compute_returnsFilledMapWithAlreadySelectedPeople_whenThereAreAlreadySelectedPeople() {
        // GIVEN
        Map<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, Collections.emptySet())
                .build();

        Map<Event, Set<Person>> alreadySelected = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, new HashSet<>(Arrays.asList(person1, person2)))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                0,
                Collections.emptyMap(),
                availabilities,
                alreadySelected
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).containsKey(event1);
        //noinspection ResultOfMethodCallIgnored
        assertThat(result.get(event1)).containsExactly(person1, person2);
    }

    @Test
    public void compute_returnsNoAttendeeList_whenEventHasNoAttendeeCountDefined() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(null);

        Map<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, Collections.emptySet())
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                2,
                Collections.emptyMap(),
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void compute_returnsEmptyAttendeeList_whenEventHasAttendeeCountIsZero() {
        // GIVEN
        Mockito.when(event1.attendeesCount()).thenReturn(0);

        Map<Event, Set<Person>> availabilities = ImmutableMap.<Event, Set<Person>>builder()
                .put(event1, new HashSet<>(Collections.singletonList(person1)))
                .build();

        SimpleAttendeeSelection simpleAttendeeSelection = new SimpleAttendeeSelection(
                2,
                Collections.emptyMap(),
                availabilities,
                Collections.emptyMap()
        );

        // WHEN
        Map<Event, Set<Person>> result = simpleAttendeeSelection.selection();

        // THEN
        assertThat(result).isNotEmpty();
        assertThat(result.get(event1)).hasSize(0);
    }
}
