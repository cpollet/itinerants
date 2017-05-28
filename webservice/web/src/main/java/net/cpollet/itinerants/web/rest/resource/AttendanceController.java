package net.cpollet.itinerants.web.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.algorithm.AttendeeSelection;
import net.cpollet.itinerants.core.domain.Event;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.AttendanceService;
import net.cpollet.itinerants.core.service.EventService;
import net.cpollet.itinerants.web.rest.data.AttendanceListResponse;
import net.cpollet.itinerants.web.rest.data.AttendancePayload;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
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
    private final AttendanceService attendanceService;

    public AttendanceController(EventService eventService,
                                AttendeeSelection.Factory attendanceStrategyFactory,
                                AttendanceService attendanceService) {
        this.eventService = eventService;
        this.attendanceStrategyFactory = attendanceStrategyFactory;
        this.attendanceService = attendanceService;
    }

    @GetMapping(value = "")
    @PreAuthorize(AUTHORIZE_ADMIN)
    public AttendanceListResponse getAttendances(@RequestParam("eventId") List<String> eventIds) {
        log.info("Creating availabilities list for [{}]", eventIds.stream().collect(Collectors.joining(", ")));

        List<Event> events = eventService.getByIds(eventIds);

        Map<Event, Set<Person>> availabilities = mapEventsToPersonsSet(events, Event::availablePeople);
        Map<Event, Set<Person>> attendances = mapEventsToPersonsSet(events, Event::attendingPeople);

        List<Event> pastEvents = eventService.past(EventService.SortOrder.DESCENDING);

        Map<Person, Long> pastAttendancesCount = pastEvents.stream()
                .map(Event::attendingPeople)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        Map<Event, Set<Person>> selection = attendanceStrategyFactory.create(
                pastEvents.size(),
                pastAttendancesCount,
                availabilities,
                attendances
        ).selection();

        return new AttendanceListResponse(selection, availabilities, pastAttendancesCount, pastEvents.size());
    }

    private Map<Event, Set<Person>> mapEventsToPersonsSet(List<Event> events, Function<Event, Set<Person>> function) {
        return events.stream()
                .collect(Collectors.toMap(
                        e -> e,
                        function)
                );
    }

    @PutMapping(value = "")
    @PreAuthorize(AUTHORIZE_ADMIN)
    public void saveAttendances(@RequestBody List<AttendancePayload> attendances) {
        attendances.forEach(a -> {
            log.info("Updating attendance: {}", a);
            attendanceService.update(new AttendanceService.InputAttendanceData(a.getEventId(), a.getPersonIds()));
        });
    }
}

