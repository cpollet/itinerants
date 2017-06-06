package net.cpollet.itinerants.web.rest.resource;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.PersonService;
import net.cpollet.itinerants.web.authentication.AuthenticationPrincipal;
import net.cpollet.itinerants.web.authentication.TokenService;
import net.cpollet.itinerants.web.rest.data.LoginPayload;
import net.cpollet.itinerants.web.rest.data.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<LoginResponse> create(@RequestBody LoginPayload credentials) {
        log.info("Creating session for username={}", credentials.getUsername());
        Person person = personService.getByUsername(credentials.getUsername());

        if (person == null) {
            return new ResponseEntity<>(LoginResponse.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        if (!person.password().matches(credentials.getPassword())) {
            return new ResponseEntity<>(LoginResponse.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        String token = UUID.randomUUID().toString();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new AuthenticationPrincipal(credentials.getUsername(), person.id()),
                credentials.getPassword(),
                person.roles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                        .collect(Collectors.toList())
        );

        tokenService.store(token, authentication);

        return new ResponseEntity<>(new LoginResponse(token, person.id(), person.roles()), HttpStatus.OK);
    }
}
