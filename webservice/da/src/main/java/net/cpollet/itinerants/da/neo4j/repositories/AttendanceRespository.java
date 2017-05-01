package net.cpollet.itinerants.da.neo4j.repositories;

import net.cpollet.itinerants.da.neo4j.data.relations.IsAttending;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by cpollet on 01.05.17.
 */
public interface AttendanceRespository extends GraphRepository<IsAttending> {
    @Query("MATCH (e:Event), (p:Person) " +
            "WHERE e.uuid = {eventId} AND p.uuid = {personId} " +
            "MERGE (p)-[:IS_ATTENDING]->(e)")
    void create(@Param("personId") String personId, @Param("eventId") String eventId);

    @Query("MATCH (p:Person)-[r:IS_ATTENDING]->(e:Event) " +
            "WHERE e.uuid = {eventId} " +
            "DELETE r")
    void deleteAllForEvent(@Param("eventId") String eventId);
}
