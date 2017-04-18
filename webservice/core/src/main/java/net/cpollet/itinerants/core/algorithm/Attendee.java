package net.cpollet.itinerants.core.algorithm;

import lombok.Getter;
import net.cpollet.itinerants.core.domain.Person;

import java.util.Objects;

/**
 * Created by cpollet on 06.04.17.
 */
@Getter
public class Attendee {
    private final int participationCount;
    private final Person person;

    public Attendee(Person person, int participationCount) {
        this.person = person;
        this.participationCount = participationCount;
    }

    public Attendee(Attendee attendee, int participationCount) {
        person = attendee.person;
        this.participationCount = participationCount;
    }

    public float targetRatio() {
        return person.targetRatio();
    }

    public String id() {
        return person.id();
    }

    public String name() {
        return person.name();
    }

    public Attendee withIncreasedCount() {
        return new Attendee(this, participationCount + 1);
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "participationCount=" + participationCount +
                ", person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendee attendee = (Attendee) o;
        return Objects.equals(person.id(), attendee.person.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(person.id());
    }
}
