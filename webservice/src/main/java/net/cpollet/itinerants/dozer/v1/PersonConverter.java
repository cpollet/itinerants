package net.cpollet.itinerants.dozer.v1;

import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.rest.v1.data.PersonData;
import org.dozer.DozerConverter;

/**
 * @author Christophe Pollet
 */
public class PersonConverter extends DozerConverter<PersonData, Person> {
    public PersonConverter() {
        super(PersonData.class, Person.class);
    }

    @Override
    public Person convertTo(PersonData from, Person to) {
        return new Person(from.id, from.name, from.email);
    }

    @Override
    public PersonData convertFrom(Person from, PersonData to) {
        if (to == null) {
            to = new PersonData();
        }

        to.id = from.getId();
        to.name = from.getName();
        to.email = from.getEmail();

        return to;
    }
}
