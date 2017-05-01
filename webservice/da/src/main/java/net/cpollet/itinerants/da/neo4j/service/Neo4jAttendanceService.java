package net.cpollet.itinerants.da.neo4j.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.service.AttendanceService;
import net.cpollet.itinerants.da.neo4j.repositories.AttendanceRespository;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 01.05.17.
 */
@Service
@Slf4j
public class Neo4jAttendanceService implements AttendanceService {
    private final AttendanceRespository attendanceRespository;

    public Neo4jAttendanceService(AttendanceRespository attendanceRespository) {
        this.attendanceRespository = attendanceRespository;
    }

    @Override
    public void update(InputAttendanceData inputAttendanceData) {
        attendanceRespository.deleteAllForEvent(inputAttendanceData.getEventId());
        inputAttendanceData.getPersonIds().forEach(p -> {
            attendanceRespository.create(p, inputAttendanceData.getEventId());
        });
    }
}
