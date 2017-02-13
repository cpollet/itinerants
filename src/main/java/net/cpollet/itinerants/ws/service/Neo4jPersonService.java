package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jPerson;
import net.cpollet.itinerants.ws.da.neo4j.repositories.PersonRepository;
import net.cpollet.itinerants.ws.service.data.Person;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 13.02.17.
 */
@Service
public class Neo4jPersonService implements PersonService {
    private final PersonRepository personRepository;

    public Neo4jPersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getById(long id) {
        Neo4jPerson event = personRepository.findOne(id);

        if (event == null) {
            throw new IllegalArgumentException("No node of type " + Neo4jPerson.class.getAnnotation(NodeEntity.class).label() + " found for id " + id);
        }

        return event;
    }

    @Override
    public long create(Person person) {
        Neo4jPerson neo4jPerson = new Neo4jPerson();
        neo4jPerson.setName(person.getName());

        return personRepository.save(neo4jPerson).getId();
    }

}
