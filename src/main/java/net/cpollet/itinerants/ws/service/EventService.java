package net.cpollet.itinerants.ws.service;


import net.cpollet.itinerants.ws.service.data.Event;
import net.cpollet.itinerants.ws.service.data.Person;

import java.util.Set;

/**
 * Created by cpollet on 11.02.17.
 */
public interface EventService {
    Event getById(long id);

    long create(Event event);

    abstract class InputEvent implements Event {
        @Override
        public Long getId() {
            throw new IllegalStateException();
        }

        @Override
        public Set<? extends Person> availablePeople() {
            throw new IllegalStateException();
        }
    }
}
