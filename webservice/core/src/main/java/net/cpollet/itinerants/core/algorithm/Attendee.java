package net.cpollet.itinerants.core.algorithm;

import lombok.Getter;
import net.cpollet.itinerants.core.domain.Person;

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

    public Attendee withIncreasedCount() {
        return new Attendee(this, participationCount + 1);
    }
}
