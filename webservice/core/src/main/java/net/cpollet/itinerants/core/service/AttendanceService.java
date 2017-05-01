package net.cpollet.itinerants.core.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by cpollet on 01.05.17.
 */
public interface AttendanceService {
    void update(InputAttendanceData inputAttendanceData);

    @Getter
    @AllArgsConstructor
    class InputAttendanceData {
        private final String eventId;
        private final List<String> personIds;
    }
}
