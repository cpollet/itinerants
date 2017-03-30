package net.cpollet.itinerants.da.neo4j.repositories;

import net.cpollet.itinerants.da.neo4j.data.Neo4JEventData;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventRepository extends GraphRepository<Neo4JEventData> {
    @Query("match (e:EventData) where e.timestamp > {timestamp} WITH e MATCH p=(e)-[*0..1]-(m) RETURN p")
    List<Neo4JEventData> future(@Param("timestamp") long timestamp, Sort sort);

    @Query("match (e:EventData) where e.timestamp < {timestamp} WITH e MATCH p=(e)-[*0..1]-(m) RETURN p")
    List<Neo4JEventData> past(@Param("timestamp") long timestamp, Sort sort);
}
