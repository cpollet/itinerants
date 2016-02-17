package net.cpollet.itinerants.dozer.v1;

import org.dozer.DozerConverter;

/**
 * @author Christophe Pollet
 */
public class PersonConverter extends DozerConverter<net.cpollet.itinerants.rest.v1.data.Person, net.cpollet.itinerants.core.api.data.Person> {
    public PersonConverter() {
        super(net.cpollet.itinerants.rest.v1.data.Person.class, net.cpollet.itinerants.core.api.data.Person.class);
    }

    @Override
    public net.cpollet.itinerants.core.api.data.Person convertTo(net.cpollet.itinerants.rest.v1.data.Person from,
                                                                 net.cpollet.itinerants.core.api.data.Person to) {
        return new net.cpollet.itinerants.core.api.data.Person(from.id, from.name, from.email);
    }

    @Override
    public net.cpollet.itinerants.rest.v1.data.Person convertFrom(net.cpollet.itinerants.core.api.data.Person from,
                                                                  net.cpollet.itinerants.rest.v1.data.Person to) {
        if (to == null) {
            to = new net.cpollet.itinerants.rest.v1.data.Person();
        }

        to.id = from.getId();
        to.name = from.getName();
        to.email = from.getEmail();

        return to;
    }
}
