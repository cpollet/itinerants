package net.cpollet.itinerants.da.neo4j.repositories;

import net.cpollet.itinerants.da.neo4j.Context;
import net.cpollet.itinerants.da.neo4j.data.Neo4JEventData;
import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by cpollet on 27.05.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Context.class})
public class ITestEventRepository {
    @Autowired
    private EventRepository eventRepository;

    @Before
    public void cleanupDatabase() {
        eventRepository.deleteAll();
    }

    @Test
    public void future_returnsFutureEvents() {
        // GIVEN
        Neo4JEventData futureEvent = new Neo4JEventData();
        futureEvent.setUUID("future");
        futureEvent.setDateTime(LocalDateTime.now().plusDays(1));

        Neo4JEventData pastEvent = new Neo4JEventData();
        pastEvent.setUUID("past");
        pastEvent.setDateTime(LocalDateTime.now().minusDays(1));

        eventRepository.save(Arrays.asList(pastEvent, futureEvent));

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(
                LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0)),
                new Sort(Sort.Direction.ASC, "e.timestamp")
        );

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).getUUID()).isEqualTo("future");
    }

    @Test
    public void future_returnsFutureEventsWhenLinkedPeople() {
        // GIVEN
        Neo4JPersonData person = new Neo4JPersonData();
        person.setUUID("person-1");

        Neo4JEventData futureEvent = new Neo4JEventData();
        futureEvent.setUUID("future");
        futureEvent.setDateTime(LocalDateTime.now().plusDays(1));
        futureEvent.setAttendingPeople(new HashSet<>(Collections.singleton(person)));

        Neo4JEventData pastEvent = new Neo4JEventData();
        pastEvent.setUUID("past");
        pastEvent.setDateTime(LocalDateTime.now().minusDays(1));
        futureEvent.setAttendingPeople(new HashSet<>(Collections.singleton(person)));


        eventRepository.save(Arrays.asList(pastEvent, futureEvent));

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(
                LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0)),
                new Sort(Sort.Direction.ASC, "e.timestamp")
        );

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).getUUID()).isEqualTo("future");
    }


    @Test
    public void future_returnsEmptyAvailablePeople_whenNobodyIsAvailable() {
        // GIVEN
        Neo4JEventData event = new Neo4JEventData();
        event.setDateTime(LocalDateTime.now());

        eventRepository.save(event);

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(0, new Sort(Sort.Direction.ASC, "e.timestamp"));

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).availablePeople()).hasSize(0);
    }

    @Test
    public void future_returnsEmptyAttendingPeople_whenNobodyIsAvailable() {
        // GIVEN
        Neo4JEventData event = new Neo4JEventData();
        event.setDateTime(LocalDateTime.now());

        eventRepository.save(event);

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(0, new Sort(Sort.Direction.ASC, "e.timestamp"));

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).attendingPeople()).hasSize(0);
    }

    @Test
    public void future_returnsAllAvailablePeople() {
        // GIVEN
        Neo4JPersonData person1 = new Neo4JPersonData();
        person1.setUUID("person-1");

        Neo4JPersonData person2 = new Neo4JPersonData();
        person2.setUUID("person-2");

        Neo4JEventData event = new Neo4JEventData();
        event.setDateTime(LocalDateTime.now());
        event.setAvailablePeople(new HashSet<>(Arrays.asList(person1, person2)));

        eventRepository.save(event);

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(0, new Sort(Sort.Direction.ASC, "e.timestamp"));

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).availablePeople()).hasSize(2);
        assertThat(futureEvents.get(0).availablePeople().stream()
                .map(Neo4JPersonData::getUUID)
                .collect(Collectors.toList()))
                .containsExactly(person1.getUUID(), person2.getUUID());
    }

    @Test
    public void future_returnsAllAttendingPeople() {
        // GIVEN
        Neo4JPersonData person1 = new Neo4JPersonData();
        person1.setUUID("person-1");

        Neo4JPersonData person2 = new Neo4JPersonData();
        person2.setUUID("person-2");

        Neo4JEventData event = new Neo4JEventData();
        event.setDateTime(LocalDateTime.now());
        event.setAttendingPeople(new HashSet<>(Arrays.asList(person1, person2)));

        eventRepository.save(event);

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(0, new Sort(Sort.Direction.ASC, "e.timestamp"));

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).attendingPeople()).hasSize(2);
        assertThat(futureEvents.get(0).attendingPeople().stream()
                .map(Neo4JPersonData::getUUID)
                .collect(Collectors.toList()))
                .containsExactly(person1.getUUID(), person2.getUUID());
    }

    @Test
    public void future_returnsOnlyCurrentlyAvailablePerson() {
        // GIVEN
        Neo4JPersonData person1 = new Neo4JPersonData();
        person1.setUUID("person-1");
        person1.setUsername("person-1");

        Neo4JPersonData person2 = new Neo4JPersonData();
        person2.setUUID("person-2");
        person2.setUsername("person-2");

        Neo4JEventData event = new Neo4JEventData();
        event.setDateTime(LocalDateTime.now());
        event.setAvailablePeople(new HashSet<>(Arrays.asList(person1, person2)));

        eventRepository.save(event);

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(0, "person-1", new Sort(Sort.Direction.ASC, "e.timestamp"));

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).availablePeople()).hasSize(1);
        assertThat(futureEvents.get(0).availablePeople().stream()
                .map(Neo4JPersonData::getUUID)
                .collect(Collectors.toList()))
                .containsExactly(person1.getUUID());
    }

    @Test
    public void future_returnsOnlyCurrentlyAttendingPerson() {
        // GIVEN
        Neo4JPersonData person1 = new Neo4JPersonData();
        person1.setUUID("person-1");
        person1.setUsername("person-1");

        Neo4JPersonData person2 = new Neo4JPersonData();
        person2.setUUID("person-2");
        person2.setUsername("person-2");

        Neo4JEventData event = new Neo4JEventData();
        event.setDateTime(LocalDateTime.now());
        event.setAttendingPeople(new HashSet<>(Arrays.asList(person1, person2)));

        eventRepository.save(event);

        // WHEN
        List<Neo4JEventData> futureEvents = eventRepository.future(0, "person-1", new Sort(Sort.Direction.ASC, "e.timestamp"));

        // THEN
        assertThat(futureEvents).hasSize(1);
        assertThat(futureEvents.get(0).attendingPeople()).hasSize(1);
        assertThat(futureEvents.get(0).attendingPeople().stream()
                .map(Neo4JPersonData::getUUID)
                .collect(Collectors.toList()))
                .containsExactly(person1.getUUID());
    }
}
