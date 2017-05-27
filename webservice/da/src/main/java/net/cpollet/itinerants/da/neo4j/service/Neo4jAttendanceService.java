package net.cpollet.itinerants.da.neo4j.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.service.AttendanceService;
import net.cpollet.itinerants.da.neo4j.repositories.AttendanceRepository;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 01.05.17.
 */
@Service
@Slf4j
public class Neo4jAttendanceService implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public Neo4jAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public void update(InputAttendanceData inputAttendanceData) {
        attendanceRepository.deleteAllForEvent(inputAttendanceData.getEventId());
        inputAttendanceData.getPersonIds().forEach(p -> {
            attendanceRepository.create(p, inputAttendanceData.getEventId());
        });
    }
}
