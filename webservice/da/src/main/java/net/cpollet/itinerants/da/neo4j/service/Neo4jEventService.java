package net.cpollet.itinerants.da.neo4j.service;

import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.da.neo4j.data.Neo4JEventData;
import net.cpollet.itinerants.da.neo4j.repositories.EventRepository;
import net.cpollet.itinerants.core.domain.data.EventData;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public EventData getByUUID(String uuid) {
        Neo4JEventData eventData = eventRepository.findOneByUUID(uuid);

        if (eventData == null) {
            throw new IllegalArgumentException("No node of type " + Neo4JEventData.class.getAnnotation(NodeEntity.class).label() + " found for UUID " + uuid);
        }

        return eventData;
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
    public List<EventData> future(SortOrder sortOrder) {
        long fromTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));
        // noinspection unchecked
        return (List<EventData>) (List<?>) eventRepository.future(fromTimestamp, sortOrderNeo4jMap.get(sortOrder));
    }

    @Override
    public List<EventData> future(String username, SortOrder sortOrder) {
        long fromTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));
        // noinspection unchecked
        return (List<EventData>) (List<?>) eventRepository.future(fromTimestamp, username, sortOrderNeo4jMap.get(sortOrder));
    }

    @Override
    public List<EventData> past(SortOrder sortOrder) {
        long toTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0));
        //noinspection unchecked
        return (List<EventData>) (List<?>) eventRepository.past(toTimestamp, sortOrderNeo4jMap.get(sortOrder));
    }
}
