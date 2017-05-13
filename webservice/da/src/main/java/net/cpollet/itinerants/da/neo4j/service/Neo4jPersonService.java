package net.cpollet.itinerants.da.neo4j.service;

import net.cpollet.itinerants.core.domain.data.PersonData;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.da.neo4j.data.Neo4JPersonData;
import net.cpollet.itinerants.da.neo4j.repositories.PersonRepository;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public PersonData getById(String id) {
        Neo4JPersonData personData = personRepository.findOneByUUID(id);

        if (personData == null) {
            throw new IllegalArgumentException("No node of type " + Neo4JPersonData.class.getAnnotation(NodeEntity.class).label() + " found for UUID " + id);
        }

        return personData;
    }

    @Override
    public String create(PersonData personData) {
        // FIXME fails with a nasty exception when username already exists...
        Neo4JPersonData neo4jPerson = new Neo4JPersonData();
        neo4jPerson.setFirstName(personData.getFirstName());
        neo4jPerson.setLastName(personData.getLastName());
        neo4jPerson.setUsername(personData.getUsername());
        neo4jPerson.setPassword("-");
        neo4jPerson.setUUID(UUID.randomUUID().toString());

        return personRepository.save(neo4jPerson).getUUID();
    }

    @Override
    public PersonData getByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
