package net.cpollet.itinerants.ws.da.neo4j.data;

import lombok.Data;
import net.cpollet.itinerants.ws.service.data.Event;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
@Data
@NodeEntity(label = "Event")
public class Neo4jEvent implements Event {
    @GraphId
    private Long id;
    private String name;
    private String dateTime;

    @Relationship(direction = Relationship.INCOMING, type = "IS_AVAILABLE")
    private Set<Neo4jPerson> availablePeople;

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.parse(dateTime);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime);
    }

    @Override
    public Set<Neo4jPerson> availablePeople() {
        if (availablePeople == null) {
            return availablePeople = new HashSet<>();
        }
        return availablePeople;
    }
}
