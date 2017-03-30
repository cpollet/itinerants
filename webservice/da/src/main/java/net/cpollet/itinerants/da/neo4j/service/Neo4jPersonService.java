package net.cpollet.itinerants.da.neo4j.service;

import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import net.cpollet.itinerants.da.neo4j.repositories.PersonRepository;
import net.cpollet.itinerants.core.domain.data.PersonData;
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
    public PersonData getById(long id) {
        Neo4JPersonData personData = personRepository.findOne(id);

        if (personData == null) {
            throw new IllegalArgumentException("No node of type " + Neo4JPersonData.class.getAnnotation(NodeEntity.class).label() + " found for id " + id);
        }

        return personData;
    }

    @Override
    public long create(PersonData personData) {
        // FIXME fails with a nasty exception when username already exists...
        Neo4JPersonData neo4jPerson = new Neo4JPersonData();
        neo4jPerson.setName(personData.getName());
        neo4jPerson.setUsername(personData.getUsername());
        neo4jPerson.setPassword(personData.getPassword());

        return personRepository.save(neo4jPerson).getId();
    }

    @Override
    public PersonData getByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
