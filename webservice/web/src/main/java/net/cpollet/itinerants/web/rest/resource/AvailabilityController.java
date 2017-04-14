package net.cpollet.itinerants.web.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.service.AvailabilityService;
import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.web.rest.data.AvailabilityPayload;
import net.cpollet.itinerants.web.rest.data.AvailabilityResponse;
import net.cpollet.itinerants.web.rest.data.EventResponse;
import net.cpollet.itinerants.web.rest.data.PersonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private static final String AUTHORIZE_OWN_OR_ADMIN = "principal.personId == #availability.personId or hasRole('ADMIN')";

    private final AvailabilityService availabilityService;
    private final PersonService personService;
    private final EventService eventService;

    public AvailabilityController(AvailabilityService availabilityService, PersonService personService, EventService eventService) {
        this.availabilityService = availabilityService;
        this.personService = personService;
        this.eventService = eventService;
    }

    @PutMapping(value = "")
    @PreAuthorize(AUTHORIZE_OWN_OR_ADMIN)
    public AvailabilityResponse create(@RequestBody AvailabilityPayload availability) {
        log.info("Creating {}", availability);

        availabilityService.create(
                AvailabilityService.InputAvailabilityData.delete(
                        availability.getPersonId(),
                        availability.getEventId()
                )
        );

        return new AvailabilityResponse(
                new PersonResponse(personService.getById(availability.getPersonId())),
                new EventResponse(eventService.getById(availability.getEventId()))
        );
    }

    @DeleteMapping(value = "")
    @PreAuthorize(AUTHORIZE_OWN_OR_ADMIN)
    public void delete(@RequestBody AvailabilityPayload availability) {
        log.info("Deleting {}", availability);

        availabilityService.delete(
                AvailabilityService.InputAvailabilityData.delete(
                        availability.getPersonId(),
                        availability.getEventId()
                )
        );
    }
}
