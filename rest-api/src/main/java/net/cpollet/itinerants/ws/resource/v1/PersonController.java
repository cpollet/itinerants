package net.cpollet.itinerants.ws.resource.v1;

import net.cpollet.itinerants.ws.api.v1.data.CreatePersonPayload;
import net.cpollet.itinerants.ws.api.v1.data.PersonResponse;
import net.cpollet.itinerants.ws.service.PersonService;
import net.cpollet.itinerants.ws.service.data.Event;
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
    public PersonResponse create(@RequestBody CreatePersonPayload person) {
        long personId = personService.create(new PersonService.InputPerson() {
            @Override
            public String getName() {
                return person.getName();
            }

            @Override
            public String getUsername() {
                return person.getUsername();
            }

            @Override
            public String getPassword() {
                return person.getPassword();
            }

            @Override
            public void availableFor(Event event) {
                throw new IllegalStateException();
            }
        });

        return new PersonResponse(personService.getById(personId));
    }
}
