package net.cpollet.itinerants.core.service;

import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.domain.data.EventData;
import net.cpollet.itinerants.core.domain.data.PersonData;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonService {
    Person getById(String id);

    Person create(PersonData personData);

    Person getByUsername(String username);

    Person save(Person person);

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

        @Override
        public void setPassword(String password) {
            // do nothing
        }

        @Override
        public void setFirstName(String firstName) {
            // do nothing
        }

        @Override
        public void setLastName(String lastName) {
            // do nothing
        }

        @Override
        public void setEmail(String email) {
            // do nothing
        }
    }
}
