package net.cpollet.itinerants.ws.resource.v1;

import net.cpollet.itinerants.ws.api.v1.data.LoginPayload;
import net.cpollet.itinerants.ws.api.v1.data.LoginResponse;
import net.cpollet.itinerants.ws.authentication.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by cpollet on 14.03.17.
 */
@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final TokenService tokenService;

    public SessionController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PutMapping(value = "")
    public LoginResponse create(@RequestBody LoginPayload credentials) {
        if (!credentials.getUsername().equals("username")) {
            return LoginResponse.INVALID_CREDENTIALS;
        }

        String token = UUID.randomUUID().toString();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        tokenService.store(token, authentication);

        return new LoginResponse(token);
    }
}