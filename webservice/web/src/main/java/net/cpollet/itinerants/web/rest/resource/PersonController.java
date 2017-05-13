package net.cpollet.itinerants.web.rest.resource;

import net.cpollet.itinerants.core.domain.Password;
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
    private final Password.Factory passwordFactory;

    public PersonController(PersonService personService, Password.Factory passwordFactory) {
        this.personService = personService;
        this.passwordFactory = passwordFactory;
    }

    @PostMapping(value = "")
    public PersonResponse create(@RequestBody CreatePersonPayload person) {
        String personId = personService.create(new PersonService.InputPersonData() {
            @Override
            public String getFirstName() {
                return person.getFirstName();
            }

            @Override
            public String getLastName() {
                return person.getLastName();
            }

            @Override
            public String getEmail() {
                return person.getEmail();
            }

            @Override
            public String getUsername() {
                return person.getUsername();
            }
        });

        return new PersonResponse(personService.getById(personId));
    }
}
