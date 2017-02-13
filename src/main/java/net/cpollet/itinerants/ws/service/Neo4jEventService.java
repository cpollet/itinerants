package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEvent;
import net.cpollet.itinerants.ws.da.neo4j.repositories.EventRepository;
import net.cpollet.itinerants.ws.service.data.Event;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 11.02.17.
 */
@Service
public class Neo4jEventService implements EventService {
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
}
