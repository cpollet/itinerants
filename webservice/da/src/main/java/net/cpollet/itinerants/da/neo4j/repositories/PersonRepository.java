package net.cpollet.itinerants.da.neo4j.repositories;

import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonRepository extends GraphRepository<Neo4JPersonData> {
    @Query("match (p:Person) where p.username = {0} return p")
    Neo4JPersonData findByUsername(String username);
}
