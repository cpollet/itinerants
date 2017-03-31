package net.cpollet.itinerants.core.service;

import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.domain.data.PersonData;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonService {
    PersonData getByUUID(String uuid);

    String create(PersonData personData);

    PersonData getByUsername(String username);

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
    }
}
