package net.cpollet.itinerants.da.neo4j.service;

import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.domain.data.PersonData;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import net.cpollet.itinerants.da.neo4j.repositories.PersonRepository;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by cpollet on 13.02.17.
 */
@Service
public class Neo4jPersonService implements PersonService {
    private final PersonRepository personRepository;
    private final Password.Factory passwordFactory;
    private final Person.Notifier notifier;

    public Neo4jPersonService(PersonRepository personRepository, Password.Factory passwordFactory, Person.Notifier notifier) {
        this.personRepository = personRepository;
        this.passwordFactory = passwordFactory;
        this.notifier = notifier;
    }

    @Override
    public Person getById(String id) {
        Neo4JPersonData personData = personRepository.findOneByUUID(id);

        if (personData == null) {
            throw new IllegalArgumentException("No node of type " + Neo4JPersonData.class.getAnnotation(NodeEntity.class).label() + " found for UUID " + id);
        }

        return build(personData);
    }

    private Person build(PersonData personData) {
        PersonData nonNullPersonData = Optional
                .ofNullable(personData)
                .orElse(PersonData.EMPTY);

        return new Person(nonNullPersonData, passwordFactory, notifier);
    }

    @Override
    public Person create(PersonData personData) {
        // FIXME fails with a nasty exception when username already exists...
        Neo4JPersonData neo4jPerson = new Neo4JPersonData();
        neo4jPerson.setFirstName(personData.getFirstName());
        neo4jPerson.setLastName(personData.getLastName());
        neo4jPerson.setUsername(personData.getUsername());
        neo4jPerson.setPassword("-");
        neo4jPerson.setEmail(personData.getEmail());
        neo4jPerson.setRoles(personData.getRoles());
        neo4jPerson.setTargetRatio(personData.getTargetRatio());
        neo4jPerson.setUUID(UUID.randomUUID().toString());

        return build(personRepository.save(neo4jPerson));
    }

    @Override
    public Person getByUsername(String username) {
        return build(personRepository.findByUsername(username));
    }

    @Override
    public Person save(Person person) {
        Neo4JPersonData personData = personRepository.findOneByUUID(person.id());

        return build(personRepository.save(personData));
    }
}
