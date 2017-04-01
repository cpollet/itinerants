package net.cpollet.itinerants.core.service;

import lombok.Getter;

/**
 * Created by cpollet on 13.02.17.
 */
public interface AvailabilityService {
    void create(InputAvailabilityData availability);

    void delete(InputAvailabilityData availability);

    enum Mode {
        CREATE, DELETE
    }

    @Getter
    class InputAvailabilityData {
        private final String personId;
        private final String eventId;
        private final Mode mode;

        private InputAvailabilityData(String personId, String eventId, Mode mode) {
            this.personId = personId;
            this.eventId = eventId;
            this.mode = mode;
        }

        public static InputAvailabilityData create(String personId, String eventId) {
            return new InputAvailabilityData(personId, eventId, Mode.CREATE);
        }

        public static InputAvailabilityData delete(String personId, String eventId) {
            return new InputAvailabilityData(personId, eventId, Mode.DELETE);
        }
    }
}
