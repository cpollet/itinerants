package net.cpollet.itinerants.ws.resource.v1;

import net.cpollet.itinerants.ws.api.v1.data.AvailabilityPayload;
import net.cpollet.itinerants.ws.api.v1.data.AvailabilityResponse;
import net.cpollet.itinerants.ws.api.v1.data.EventResponse;
import net.cpollet.itinerants.ws.api.v1.data.PersonResponse;
import net.cpollet.itinerants.ws.service.AvailabilityService;
import net.cpollet.itinerants.ws.service.EventService;
import net.cpollet.itinerants.ws.service.PersonService;
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
        availabilityService.create(new AvailabilityService.InputAvailability() {
            @Override
            public long getPersonId() {
                return availability.getPersonId();
            }

            @Override
            public long getEventId() {
                return availability.getEventId();
            }
        });

        return new AvailabilityResponse(
                new PersonResponse(personService.getById(availability.getPersonId())),
                new EventResponse(eventService.getById(availability.getEventId()))
        );
    }

    @DeleteMapping(value = "")
    public void delete(@RequestBody AvailabilityPayload availability) {
        availabilityService.delete(new AvailabilityService.InputAvailability(){
            @Override
            public long getPersonId() {
                return availability.getPersonId();
            }

            @Override
            public long getEventId() {
                return availability.getEventId();
            }
        });
    }

}
