package net.cpollet.itinerants.da.neo4j.repositories;

import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonRepository extends GraphRepository<Neo4JPersonData> {
    @Query("MATCH (p:Person) " +
            "WHERE p.username = {username} " +
            "RETURN p")
    Neo4JPersonData findByUsername(@Param("username") String username);

    Neo4JPersonData findOneByUUID(@Param("uuid") String uuid);
}
