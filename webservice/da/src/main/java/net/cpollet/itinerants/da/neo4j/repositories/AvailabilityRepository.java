package net.cpollet.itinerants.da.neo4j.repositories;

import net.cpollet.itinerants.da.neo4j.data.relations.IsAvailable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by cpollet on 01.04.17.
 */
public interface AvailabilityRepository extends GraphRepository<IsAvailable> {
    @Query("MATCH (e:Event), (p:Person) " +
            "WHERE e.uuid = {eventId} AND p.uuid = {personId} " +
            "MERGE (p)-[:IS_AVAILABLE]->(e)")
    void create(@Param("personId") String personId, @Param("eventId") String eventId);

    @Query("MATCH (p:Person)-[r:IS_AVAILABLE]->(e:Event) " +
            "WHERE p.uuid = {personId} AND e.uuid = {eventId} " +
            "DELETE r")
    void delete(@Param("personId") String personId, @Param("eventId") String eventId);

}
