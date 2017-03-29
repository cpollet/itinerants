package net.cpollet.itinerants.ws.resource.v1;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.ws.api.v1.data.LoginPayload;
import net.cpollet.itinerants.ws.api.v1.data.LoginResponse;
import net.cpollet.itinerants.ws.authentication.TokenService;
import net.cpollet.itinerants.ws.domain.Person;
import net.cpollet.itinerants.ws.domain.data.PersonData;
import net.cpollet.itinerants.ws.service.PersonService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by cpollet on 14.03.17.
 */
@RestController
@RequestMapping("/sessions")
@Slf4j
public class SessionController {
    private final TokenService tokenService;
    private final PersonService personService;
    private final Person.Factory personFactory;

    public SessionController(TokenService tokenService, PersonService personService, Person.Factory personFactory) {
        this.tokenService = tokenService;
        this.personService = personService;
        this.personFactory = personFactory;
    }

    @PutMapping(value = "")
    public LoginResponse create(@RequestBody LoginPayload credentials) {
        PersonData personData = personService.getByUsername(credentials.getUsername());

        if (personData == null) {
            return LoginResponse.INVALID_CREDENTIALS;
        }

        Person person = personFactory.create(personData);

        if (!person.password().matches(credentials.getPassword())) {
            return LoginResponse.INVALID_CREDENTIALS;
        }

        String token = UUID.randomUUID().toString();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        tokenService.store(token, authentication);

        return new LoginResponse(token, personData.getId(), new HashSet<>(Collections.singleton("user")));
    }
}
