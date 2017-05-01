package net.cpollet.itinerants.da.neo4j.data.relations;

import lombok.Data;
import net.cpollet.itinerants.da.neo4j.data.Neo4JEventData;
import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by cpollet on 01.05.17.
 */
@Data
@RelationshipEntity(type = "IS_ATTENDING")
public class IsAttending {
    @StartNode
    private final Neo4JEventData event;
    @EndNode
    private final Neo4JPersonData person;
}
