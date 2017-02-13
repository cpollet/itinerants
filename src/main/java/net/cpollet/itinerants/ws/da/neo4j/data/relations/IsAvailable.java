package net.cpollet.itinerants.ws.da.neo4j.data.relations;

import lombok.Data;
import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEvent;
import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jPerson;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
@RelationshipEntity(type = "IS_AVAILABLE")
public class IsAvailable {
    @StartNode
    private final Neo4jEvent event;
    @EndNode
    private final Neo4jPerson person;
}
