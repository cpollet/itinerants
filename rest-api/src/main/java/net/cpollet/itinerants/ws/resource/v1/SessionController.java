package net.cpollet.itinerants.ws.resource.v1;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.ws.api.v1.data.LoginPayload;
import net.cpollet.itinerants.ws.api.v1.data.LoginResponse;
import net.cpollet.itinerants.ws.authentication.TokenService;
import net.cpollet.itinerants.ws.service.PersonService;
import net.cpollet.itinerants.ws.service.data.Person;
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

    public SessionController(TokenService tokenService, PersonService personService) {
        this.tokenService = tokenService;
        this.personService = personService;
    }

    @PutMapping(value = "")
    public LoginResponse create(@RequestBody LoginPayload credentials) {
        Person person = personService.getByUsername(credentials.getUsername());

        if (person == null) {
            return LoginResponse.INVALID_CREDENTIALS;
        }

        // TODO move this to proper service...
        if (!person.getPassword().equals(credentials.getPassword())) {
            return LoginResponse.INVALID_CREDENTIALS;
        }

        String token = UUID.randomUUID().toString();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        tokenService.store(token, authentication);

        return new LoginResponse(token, person.getId(), new HashSet<>(Collections.singleton("user")));
    }
}
