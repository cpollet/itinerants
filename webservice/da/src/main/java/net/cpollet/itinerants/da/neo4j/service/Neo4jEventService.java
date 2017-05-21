package net.cpollet.itinerants.da.neo4j.service;

import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.da.neo4j.data.Neo4JEventData;
import net.cpollet.itinerants.da.neo4j.repositories.EventRepository;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 11.02.17.
 */
@Service
public class Neo4jEventService implements EventService {
    private final EventRepository eventRepository;
    private final Person.Factory personFactory;

    public Neo4jEventService(EventRepository eventRepository, Person.Factory personFactory) {
        this.eventRepository = eventRepository;
        this.personFactory = personFactory;
    }

    @Override
    public Event getById(String id) {
        Neo4JEventData eventData = eventRepository.findOneByUUID(id);

        if (eventData == null) {
            throw new IllegalArgumentException("No node of type " + Neo4JEventData.class.getAnnotation(NodeEntity.class).label() + " found for UUID " + id);
        }

        return new Event(eventData, personFactory);
    }

    @Override
    public List<Event> getByIds(List<String> ids) {
        List<Neo4JEventData> events = eventRepository.findByUUID(ids);

        if (events == null || events.isEmpty()) {
            return Collections.emptyList();
        }

        return events.stream()
                .map((eventData) -> new Event(eventData, personFactory))
                .collect(Collectors.toList());
    }

    @Override
    public String create(EventData eventData) {
        Neo4JEventData neo4jEvent = new Neo4JEventData();
        neo4jEvent.setName(eventData.getName());
        neo4jEvent.setDateTime(eventData.getDateTime());
        neo4jEvent.setUUID(UUID.randomUUID().toString());

        return eventRepository.save(neo4jEvent).getUUID();
    }

    @Override
    public List<Event> future(SortOrder sortOrder) {
        long fromTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));

        return eventRepository.future(fromTimestamp, Neo4jSortOrder.decode(sortOrder).sort()).stream()
                .map(e -> new Event(e, personFactory))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> future(String username, SortOrder sortOrder) {
        long fromTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));

        return eventRepository.future(fromTimestamp, username, Neo4jSortOrder.decode(sortOrder).sort()).stream()
                .map(e -> new Event(e, personFactory))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> past(SortOrder sortOrder) {
        long toTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));

        return eventRepository.past(toTimestamp, Neo4jSortOrder.decode(sortOrder).sort()).stream()
                .map(e -> new Event(e, personFactory))
                .collect(Collectors.toList());
    }

    enum Neo4jSortOrder {
        ASC(SortOrder.ASCENDING, new Sort(Sort.Direction.ASC, "e.timestamp")),
        DESC(SortOrder.DESCENDING, new Sort(Sort.Direction.DESC, "e.timestamp"));

        private final SortOrder sortOrder;
        private final Sort sort;

        Neo4jSortOrder(SortOrder sortOrder, Sort sort) {
            this.sortOrder = sortOrder;
            this.sort = sort;
        }

        static Neo4jSortOrder decode(SortOrder sortOrder) {
            return Arrays.stream(values())
                    .filter(v -> v.sortOrder == sortOrder)
                    .findFirst()
                    .orElse(ASC);
        }

        Sort sort() {
            return sort;
        }
    }
}
