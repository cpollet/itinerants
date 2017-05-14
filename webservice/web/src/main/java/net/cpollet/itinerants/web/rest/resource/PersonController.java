package net.cpollet.itinerants.web.rest.resource;

import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.PasswordService;
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
    private final PasswordService passwordService;

    public PersonController(PersonService personService, PasswordService passwordService) {
        this.personService = personService;
        this.passwordService = passwordService;
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

        String token = passwordService.reset(person.username());
        person.notifyCreation(() -> token);

        return new PersonResponse(person);
    }
}
