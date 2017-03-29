package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4JEventData;
import net.cpollet.itinerants.ws.da.neo4j.data.Neo4JPersonData;
import net.cpollet.itinerants.ws.da.neo4j.repositories.EventRepository;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 13.02.17.
 */
@Service
public class Neo4jAvailabilityService implements AvailabilityService {
    private final EventService eventService;
    private final PersonService personService;
    private final EventRepository eventRepository;

    public Neo4jAvailabilityService(Neo4jEventService eventService, PersonService personService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.personService = personService;
        this.eventRepository = eventRepository;
    }

    @Override
    public void create(InputAvailability availability) {
        Neo4JEventData eventData = (Neo4JEventData) eventService.getById(availability.getEventId());
        Neo4JPersonData personData = (Neo4JPersonData) personService.getById(availability.getPersonId());

        eventData.availablePeople().add(personData);
        eventRepository.save(eventData);
    }

    @Override
    public void delete(InputAvailability availability) {
        Neo4JEventData eventData = (Neo4JEventData) eventService.getById(availability.getEventId());
        Neo4JPersonData personData = (Neo4JPersonData) personService.getById(availability.getPersonId());

        eventData.availablePeople().remove(personData);
        eventRepository.save(eventData);
    }
}
