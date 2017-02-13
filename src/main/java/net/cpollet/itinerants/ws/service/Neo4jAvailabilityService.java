package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEvent;
import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jPerson;
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
        Neo4jEvent event = (Neo4jEvent) eventService.getById(availability.getEventId());
        Neo4jPerson person = (Neo4jPerson) personService.getById(availability.getPersonId());

        event.availablePeople().add(person);
        eventRepository.save(event);
    }
}
