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
    @Query("MATCH (e:Event) " +
            "WHERE e.timestamp > {timestamp} " +
            "OPTIONAL MATCH path=(e)-[]-(p:Person) " +
            "RETURN e, p, path")
    List<Neo4JEventData> future(@Param("timestamp") long timestamp, Sort sort);

    @Query("MATCH (e:Event) " +
            "WHERE e.timestamp > {timestamp} " +
            "OPTIONAL MATCH path=(e)-[]-(p:Person {username: {username}}) " +
            "RETURN e, p, path")
    List<Neo4JEventData> future(@Param("timestamp") long timestamp, @Param("username") String username, Sort sort);

    @Query("MATCH (e:Event) " +
            "WHERE e.timestamp < {timestamp} " +
            "OPTIONAL MATCH path=(e)-[]-(p:Person) " +
            "RETURN e, p, path")
    List<Neo4JEventData> past(@Param("timestamp") long timestamp, Sort sort);

    Neo4JEventData findOneByUUID(@Param("uuid") String uuid);

    @Query("MATCH (n:Event) " +
            "WHERE n.uuid IN {uuid} " +
            "WITH n " +
            "MATCH p=(n)-[*0..1]-(m) " +
            "RETURN p, id(n)")
    List<Neo4JEventData> findByUUID(@Param("uuid") List<String> uuids);
}
