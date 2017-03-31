package net.cpollet.itinerants.web.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.service.AvailabilityService;
import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.web.rest.data.AvailabilityPayload;
import net.cpollet.itinerants.web.rest.data.AvailabilityResponse;
import net.cpollet.itinerants.web.rest.data.EventResponse;
import net.cpollet.itinerants.web.rest.data.PersonResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cpollet on 13.02.17.
 */
@RestController
@RequestMapping("/availabilities")
@Slf4j
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private final PersonService personService;
    private final EventService eventService;

    public AvailabilityController(AvailabilityService availabilityService, PersonService personService, EventService eventService) {
        this.availabilityService = availabilityService;
        this.personService = personService;
        this.eventService = eventService;
    }

    @PutMapping(value = "")
    public AvailabilityResponse create(@RequestBody AvailabilityPayload availability) {
        log.info("Creating {}", availability);
        availabilityService.create(new AvailabilityService.InputAvailability() {
            @Override
            public String getPersonId() {
                return availability.getPersonId();
            }

            @Override
            public String getEventId() {
                return availability.getEventId();
            }
        });

        return new AvailabilityResponse(
                new PersonResponse(personService.getByUUID(availability.getPersonId())),
                new EventResponse(eventService.getByUUID(availability.getEventId()))
        );
    }

    @DeleteMapping(value = "")
    public void delete(@RequestBody AvailabilityPayload availability) {
        log.info("Deleting {}", availability);
        availabilityService.delete(new AvailabilityService.InputAvailability(){
            @Override
            public String getPersonId() {
                return availability.getPersonId();
            }

            @Override
            public String getEventId() {
                return availability.getEventId();
            }
        });
    }
}
