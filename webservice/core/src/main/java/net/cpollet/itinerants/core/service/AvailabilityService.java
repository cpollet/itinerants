package net.cpollet.itinerants.core.service;

/**
 * Created by cpollet on 13.02.17.
 */
public interface AvailabilityService {
    void create(InputAvailability availability);

    void delete(InputAvailability availability);

    interface InputAvailability {
        long getPersonId();

        long getEventId();
    }
}
