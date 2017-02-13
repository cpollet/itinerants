package net.cpollet.itinerants.ws.resource.v1;

import net.cpollet.itinerants.ws.api.v1.data.EventPayload;
import net.cpollet.itinerants.ws.api.v1.data.EventResponse;
import net.cpollet.itinerants.ws.service.EventService;
import net.cpollet.itinerants.ws.service.data.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by cpollet on 11.02.17.
 */
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/{id}")
    public EventResponse get(@PathVariable("id") long id) {
        Event e = eventService.getById(id);
        return new EventResponse(e);
    }

    @PostMapping(value = "")
    public EventResponse create(@RequestBody EventPayload event) {
        long eventId = eventService.create(new EventService.InputEvent() {
            @Override
            public String getName() {
                return event.getName();
            }

            @Override
            public LocalDateTime getDateTime() {
                return event.getDateTime();
            }
        });

        return new EventResponse(eventService.getById(eventId));
    }
}
