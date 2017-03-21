package net.cpollet.itinerants.dozer.v1;

import net.cpollet.itinerants.core.api.data.Person;
import net.cpollet.itinerants.rest.data.v1.PersonData;
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
        return new Person(null, from.name, from.email);
    }

    @Override
    public PersonData convertFrom(Person from, PersonData to) {
        if (to == null) {
            to = new PersonData();
        }

        to.name = from.getName();
        to.email = from.getEmail();

        return to;
    }
}
