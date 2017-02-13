package net.cpollet.itinerants.ws.da.neo4j.repositories;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jPerson;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonRepository extends GraphRepository<Neo4jPerson> {
}
