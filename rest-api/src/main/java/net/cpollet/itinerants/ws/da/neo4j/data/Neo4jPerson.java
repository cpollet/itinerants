package net.cpollet.itinerants.ws.da.neo4j.data;

import lombok.Data;
import net.cpollet.itinerants.ws.service.data.Event;
import net.cpollet.itinerants.ws.service.data.Person;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
@NodeEntity(label = "Person")
public class Neo4jPerson implements Person {
    @GraphId
    private Long id;
    private String username;
    private String name;
    private String password;

    @Override
    public void availableFor(Event event) {
        ((Neo4jEvent) event).availablePeople().add(this);
    }
}
