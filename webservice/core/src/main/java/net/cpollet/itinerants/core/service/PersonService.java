package net.cpollet.itinerants.core.service;

import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.domain.data.PersonData;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonService {
    Person getById(String id);

    String create(PersonData personData);

    Person getByUsername(String username);

    abstract class InputPersonData implements PersonData {
        @Override
        public String getUUID() {
            throw new IllegalStateException();
        }

        @Override
        public void availableFor(EventData eventData) {
            throw new IllegalStateException();
        }

        @Override
        public String getRoles() {
            throw new IllegalStateException();
        }

        @Override
        public float getTargetRatio() {
            throw new IllegalStateException();
        }

        @Override
        public String getPassword() {
            throw new IllegalStateException();
        }
    }
}
