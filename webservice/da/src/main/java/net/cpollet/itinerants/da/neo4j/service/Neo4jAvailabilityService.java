package net.cpollet.itinerants.da.neo4j.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.service.AvailabilityService;
import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import net.cpollet.itinerants.da.neo4j.repositories.AttendanceRepository;
import net.cpollet.itinerants.da.neo4j.repositories.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cpollet on 13.02.17.
 */
@Service
@Slf4j
public class Neo4jAvailabilityService implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final AttendanceRepository attendanceRepository;

    public Neo4jAvailabilityService(AvailabilityRepository availabilityRepository, AttendanceRepository attendanceRepository) {
        this.availabilityRepository = availabilityRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public void create(InputAvailabilityData availability) {
        availabilityRepository.create(availability.getPersonId(), availability.getEventId());
    }

    @Override
    public void delete(InputAvailabilityData availability) {
        if (!isAttending(availability.getPersonId(), availability.getEventId())) {
            availabilityRepository.delete(availability.getPersonId(), availability.getEventId());
        }
    }

    private boolean isAttending(String personId, String eventId) {
        List<Neo4JPersonData> attendees = attendanceRepository.attendees(eventId);
        return attendees.stream().anyMatch(p -> p.getUUID().equals(personId));
    }
}
