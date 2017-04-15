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
    @Query("MATCH (e:Event), (p), path=(e)-[*0..1]-(p) " +
            "WHERE e.timestamp > {timestamp} " +
            "RETURN path")
    List<Neo4JEventData> future(@Param("timestamp") long timestamp, Sort sort);

    @Query("MATCH (e:Event) " +
            "WHERE e.timestamp > {timestamp} " +
            "OPTIONAL MATCH (p:Person {username: {username}}), path=(e)-[:IS_AVAILABLE]-(p) " +
            "RETURN e, path")
    List<Neo4JEventData> future(@Param("timestamp") long timestamp, @Param("username") String username, Sort sort);

    @Query("MATCH (e:Event), (p), path=(e)-[*0..1]-(p) " +
            "WHERE e.timestamp < {timestamp} " +
            "RETURN path")
    List<Neo4JEventData> past(@Param("timestamp") long timestamp, Sort sort);

    @Query("MATCH (e:Event), (p), path=(e)-[*0..1]-(p) " +
            "WHERE e.timestamp < {timestamp} AND p.username = {username} " +
            "RETURN path")
    List<Neo4JEventData> past(@Param("timestamp") long timestamp, @Param("username") String username, Sort sort);

    Neo4JEventData findOneByUUID(@Param("uuid") String uuid);

    @Query("MATCH (n:Event) " +
            "WHERE n.uuid IN {uuid} " +
            "WITH n " +
            "MATCH p=(n)-[*0..1]-(m) " +
            "RETURN p, ID(n)")
    List<Neo4JEventData> findByUUID(@Param("uuid") List<String> uuids);
}
