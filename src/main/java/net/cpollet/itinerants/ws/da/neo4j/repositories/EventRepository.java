package net.cpollet.itinerants.ws.da.neo4j.repositories;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEvent;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventRepository extends GraphRepository<Neo4jEvent> {
    @Query("match (e:Event) where e.timestamp > {timestamp} WITH e MATCH p=(e)-[*0..1]-(m) RETURN p")
    List<Neo4jEvent> future(@Param("timestamp") long timestamp, Sort sort);

    @Query("match (e:Event) where e.timestamp < {timestamp} WITH e MATCH p=(e)-[*0..1]-(m) RETURN p")
    List<Neo4jEvent> past(@Param("timestamp") long timestamp, Sort sort);
}
