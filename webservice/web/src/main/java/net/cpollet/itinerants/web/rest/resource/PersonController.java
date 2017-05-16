package net.cpollet.itinerants.web.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.PasswordService;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.web.rest.data.ChangePasswordPayload;
import net.cpollet.itinerants.web.rest.data.CreatePersonPayload;
import net.cpollet.itinerants.web.rest.data.LoginPayload;
import net.cpollet.itinerants.web.rest.data.LoginResponse;
import net.cpollet.itinerants.web.rest.data.PersonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cpollet on 13.02.17.
 */
@RestController
@RequestMapping("/people")
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final PasswordService passwordService;
    private final SessionController sessionController; // I'm drunk :D

    public PersonController(PersonService personService, PasswordService passwordService, SessionController sessionController) {
        this.personService = personService;
        this.passwordService = passwordService;
        this.sessionController = sessionController;
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

            @Override
            public void setPassword(String password) {
                // do nothing
            }
        });

        String token = passwordService.generateResetToken(person.username());
        person.notifyCreation(() -> token);

        return new PersonResponse(person);
    }

    @PutMapping(value = "/{username}/passwords/{token}")
    public ResponseEntity<LoginResponse> changePassword(@PathVariable String username,
                                                        @PathVariable String token,
                                                        @RequestBody ChangePasswordPayload payload) {

        if (payload.getPassword1() == null || payload.getPassword1().length() < 8) {
            return new ResponseEntity<>(LoginResponse.PASSWORD_TOO_SHORT, HttpStatus.NOT_ACCEPTABLE);
        }

        if (!payload.getPassword1().equals(payload.getPassword2())) {
            return new ResponseEntity<>(LoginResponse.PASSWORDS_DONT_MATCH, HttpStatus.NOT_ACCEPTABLE);
        }

        Person person = passwordService.findPerson(token);

        if (!person.username().equals(username)) {
            return new ResponseEntity<>(LoginResponse.INVALID_RESET_PASSWORD_TOKEN, HttpStatus.UNAUTHORIZED);
        }

        person.password(payload.getPassword1());
        personService.save(person);

        return sessionController.create(new LoginPayload(username, payload.getPassword1()));
    }
}
