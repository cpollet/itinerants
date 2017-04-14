package net.cpollet.itinerants.web.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.algorithm.Attendee;
import net.cpollet.itinerants.core.algorithm.AttendeeSelection;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.web.rest.data.AttendanceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 14.04.17.
 */
@RestController
@RequestMapping("/attendances")
@Slf4j
public class AttendanceController {
    private static final String AUTHORIZE_ADMIN = "hasRole('ADMIN')";

    private final EventService eventService;
    private final AttendeeSelection.Factory attendanceStrategyFactory;

    public AttendanceController(EventService eventService, AttendeeSelection.Factory attendanceStrategyFactory) {
        this.eventService = eventService;
        this.attendanceStrategyFactory = attendanceStrategyFactory;
    }

    @GetMapping(value = "")
    // @PreAuthorize(AUTHORIZE_ADMIN)
    public Set<AttendanceResponse> getAttendances(@RequestParam("eventId") List<String> eventIds) {
        log.info("Creating availabilities list for [{}]", eventIds.stream().collect(Collectors.joining(", ")));

        Map<Event, Set<Attendee>> input = eventService.getByIds(eventIds).stream()
                .collect(Collectors.toMap(
                        e -> e,
                        e -> e.availablePeople().stream()
                                .map(p -> new Attendee(p, 0))
                                .collect(Collectors.toSet()))
                );

        Map<Event, Set<Attendee>> selection = attendanceStrategyFactory.create(input, 0).selection();

        return selection.entrySet().stream()
                .map(e -> new AttendanceResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }
}

