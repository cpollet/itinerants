package net.cpollet.itinerants.ws.da.neo4j.repositories;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEvent;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventRepository extends GraphRepository<Neo4jEvent> {
}
