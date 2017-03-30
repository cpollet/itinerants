package net.cpollet.itinerants.ws.rest.data;

import lombok.Data;

import java.util.Set;

/**
 * Created by cpollet on 18.03.17.
 */
@Data
public class LoginResponse {
    public final static LoginResponse INVALID_CREDENTIALS;

    private static final String CODE_SUCCESS = "SUCCESS";
    private static final String CODE_INVALID_CREDENTIALS = "INVALID_CREDENTIALS";

    static {
        INVALID_CREDENTIALS = new LoginResponse();
        INVALID_CREDENTIALS.setResult(CODE_INVALID_CREDENTIALS);
    }

    private String token;
    private Set<String> roles;
    private Long personId;
    private String result;

    private LoginResponse() {
        // nothing to do
    }

    public LoginResponse(String token, Long personId, Set<String> roles) {
        this.token = token;
        this.roles = roles;
        this.result = CODE_SUCCESS;
        this.personId = personId;
    }
}
