package net.cpollet.itinerants.web.rest.resource;

import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.web.rest.data.CreatePersonPayload;
import net.cpollet.itinerants.web.rest.data.PersonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cpollet on 13.02.17.
 */
@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "")
    public PersonResponse create(@RequestBody CreatePersonPayload personPayload) {
        Person person = personService.create(new PersonService.InputPersonData() {
            @Override
            public String getFirstName() {
                return personPayload.getFirstName();
            }

            @Override
            public String getLastName() {
                return personPayload.getLastName();
            }

            @Override
            public String getEmail() {
                return personPayload.getEmail();
            }

            @Override
            public String getUsername() {
                return personPayload.getUsername();
            }
        });

        person.notifyCreation();

        return new PersonResponse(person);
    }
}
