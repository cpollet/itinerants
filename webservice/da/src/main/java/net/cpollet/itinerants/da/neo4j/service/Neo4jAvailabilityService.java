package net.cpollet.itinerants.da.neo4j.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.service.AvailabilityService;
import net.cpollet.itinerants.da.neo4j.repositories.AvailabilityRepository;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 13.02.17.
 */
@Service
@Slf4j
public class Neo4jAvailabilityService implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    public Neo4jAvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public void create(InputAvailability availability) {
        availabilityRepository.create(availability.getPersonId(), availability.getEventId());
    }

    @Override
    public void delete(InputAvailability availability) {
        availabilityRepository.delete(availability.getPersonId(), availability.getEventId());
    }
}
