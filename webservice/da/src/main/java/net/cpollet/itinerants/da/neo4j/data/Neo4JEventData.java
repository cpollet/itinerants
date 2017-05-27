package net.cpollet.itinerants.da.neo4j.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.domain.data.PersonData;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
@Data
@NodeEntity(label = "Event")
public class Neo4JEventData implements EventData {
    @GraphId
    private Long id;
    @Property(name = "uuid")
    private String UUID;
    private String name;
    private String dateTime;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long timestamp;
    private Integer attendeesCount;

    @Relationship(direction = Relationship.UNDIRECTED, type = "IS_AVAILABLE")
    private Set<Neo4JPersonData> availablePeople;
    @Relationship(direction = Relationship.UNDIRECTED, type = "IS_ATTENDING")
    private Set<Neo4JPersonData> attendingPeople;

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.parse(dateTime);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.timestamp = dateTime.toEpochSecond(ZoneOffset.ofHours(0));
        this.dateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime);
    }

    @Override
    public Set<Neo4JPersonData> availablePeople() {
        if (availablePeople == null) {
            availablePeople = new HashSet<>();
        }
        return availablePeople;
    }

    @Override
    public Set<Neo4JPersonData> attendingPeople() {
        if (attendingPeople == null) {
            attendingPeople = new HashSet<>();
        }
        return attendingPeople;
    }
}
