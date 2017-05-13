package net.cpollet.itinerants.core.domain;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.data.PersonData;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cpollet on 29.03.17.
 */
public class Person {
    private final PersonData personData;
    private final Password.Factory passwordFactory;
    private final Notifier notifier;

    public Person(PersonData personData, Password.Factory passwordFactory, Notifier notifier) {
        this.personData = personData;
        this.passwordFactory = passwordFactory;
        this.notifier = notifier;
    }

    public String id() {
        return personData.getUUID();
    }

    public String firstName() {
        return personData.getFirstName();
    }

    public String username() {
        return personData.getUsername();
    }

    public String email() {
        return personData.getEmail();
    }

    public Password password() {
        return passwordFactory.create(personData.getPassword());
    }

    /**
     * @return the roles assigned to user, in lower case.
     */
    public Set<String> roles() {
        return Arrays.stream(personData.getRoles().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    public float targetRatio() {
        return personData.getTargetRatio();
    }

    public void notifyCreation() {
        notifier.notifyNewAccount(this);
    }

    @Override
    public String toString() {
        return "Person[" + personData.getUUID() + ": " + personData.getFirstName() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personData.getUUID(), person.personData.getUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(personData.getUUID());
    }

    public interface Factory {
        Person create(PersonData personData);
    }

    public interface Notifier {
        void notifyNewAccount(Person person);

        @Slf4j
        class LoggerNotifier implements Notifier {
            @Override
            public void notifyNewAccount(Person person) {
                log.info("Notify person created: {}", person.username());
            }
        }
    }
}
