package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEvent;
import net.cpollet.itinerants.ws.da.neo4j.repositories.EventRepository;
import net.cpollet.itinerants.ws.service.data.Event;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cpollet on 11.02.17.
 */
@Service
public class Neo4jEventService implements EventService {
    private final static Map<SortOrder, Sort> sortOrderNeo4jMap = new HashMap<>();

    static {
        sortOrderNeo4jMap.put(SortOrder.ASCENDING, new Sort(Sort.Direction.ASC, "e.timestamp"));
        sortOrderNeo4jMap.put(SortOrder.DESCENDING, new Sort(Sort.Direction.DESC, "e.timestamp"));
    }

    private final EventRepository eventRepository;

    public Neo4jEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getById(long id) {
        Neo4jEvent event = eventRepository.findOne(id);

        if (event == null) {
            throw new IllegalArgumentException("No node of type " + Neo4jEvent.class.getAnnotation(NodeEntity.class).label() + " found for id " + id);
        }

        return event;
    }

    @Override
    public long create(Event event) {
        Neo4jEvent neo4jEvent = new Neo4jEvent();
        neo4jEvent.setName(event.getName());
        neo4jEvent.setDateTime(event.getDateTime());

        return eventRepository.save(neo4jEvent).getId();
    }

    @Override
    public List<Event> future(SortOrder sortOrder) {
        long fromTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));
        //noinspection unchecked
        return (List<Event>) (List<?>) eventRepository.future(fromTimestamp, sortOrderNeo4jMap.get(sortOrder));
    }

    @Override
    public List<Event> past(SortOrder sortOrder) {
        long toTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));
        //noinspection unchecked
        return (List<Event>) (List<?>) eventRepository.past(toTimestamp, sortOrderNeo4jMap.get(sortOrder));
    }
}
