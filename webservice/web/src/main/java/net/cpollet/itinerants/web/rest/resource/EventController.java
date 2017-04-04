package net.cpollet.itinerants.web.rest.resource;

import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.web.rest.data.EventPayload;
import net.cpollet.itinerants.web.rest.data.EventResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 11.02.17.
 */
@RestController
@RequestMapping("/events")
public class EventController {
    private static final Map<String, EventService.SortOrder> stringSortOrderMap = new HashMap<>();

    static {
        stringSortOrderMap.put("asc", EventService.SortOrder.ASCENDING);
        stringSortOrderMap.put("desc", EventService.SortOrder.DESCENDING);
    }

    private final EventService eventService;

    @SuppressWarnings("unused")
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/{id}")
    public EventResponse get(@PathVariable("id") String id) {
        EventData e = eventService.getByUUID(id);
        return new EventResponse(e);
    }

    @GetMapping(value = "/future")
    public List<EventResponse> future(@RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
                                      HttpServletRequest request) {
        if (!stringSortOrderMap.containsKey(sort.toLowerCase())) {
            throw new IllegalArgumentException("Sort order " + sort + " is not a valid order");
        }

        List<EventData> events = getFutureEvents(sort, request);

        return events.stream()
                .map(EventResponse::new)
                .collect(Collectors.toList());
    }

    private List<EventData> getFutureEvents(String sort, HttpServletRequest request) {
        List<EventData> events;
        if (request.isUserInRole("ADMIN")) {
            events = eventService.future(stringSortOrderMap.get(sort.toLowerCase()));
        } else {
            events = eventService.future(request.getUserPrincipal().getName(), stringSortOrderMap.get(sort.toLowerCase()));
        }
        return events;
    }

    @GetMapping(value = "/past")
    public List<EventResponse> past(@RequestParam(name = "sort", required = false, defaultValue = "desc") String sort) {
        if (!stringSortOrderMap.containsKey(sort.toLowerCase())) {
            throw new IllegalArgumentException("Sort order " + sort + " is not a valid order");
        }

        return eventService.past(stringSortOrderMap.get(sort.toLowerCase())).stream()
                .map(EventResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "")
    public EventResponse create(@RequestBody EventPayload event) {
        String eventId = eventService.create(new EventService.InputEventData() {
            @Override
            public String getName() {
                return event.getName();
            }

            @Override
            public LocalDateTime getDateTime() {
                return event.getDateTime();
            }
        });

        return new EventResponse(eventService.getByUUID(eventId));
    }
}