package net.cpollet.itinerants.da.neo4j.data;

import lombok.Data;
import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.domain.data.PersonData;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by cpollet on 13.02.17.
 */
@Data
@NodeEntity(label = "Person")
public class Neo4JPersonData implements PersonData {
    @GraphId
    private Long id;
    @Property(name = "uuid")
    private String UUID;
    private String username;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String password;
    private String roles;
    private float targetRatio;

    @Override
    public void availableFor(EventData eventData) {
        ((Neo4JEventData) eventData).availablePeople().add(this);
    }

    public void setName(String name) {
        // nothing
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        name = firstName;
    }
}
