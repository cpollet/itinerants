package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

import java.util.Set;

/**
 * Created by cpollet on 18.03.17.
 */
@Data
public class LoginResponse {
    public static final LoginResponse INVALID_CREDENTIALS;
    public static final LoginResponse PASSWORDS_DONT_MATCH;
    public static final LoginResponse PASSWORD_TOO_SHORT;
    public static final LoginResponse INVALID_RESET_PASSWORD_TOKEN;

    private static final String CODE_SUCCESS = "SUCCESS";
    private static final String CODE_INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    private static final String CODE_PASSWORD_TOO_SHORT = "PASSWORD_TOO_SHORT";
    private static final String CODE_PASSWORDS_DONT_MATCH = "PASSWORDS_DONT_MATCH";
    private static final String CODE_INVALID_RESET_PASSWORD_TOKEN = "INVALID_RESET_PASSWORD_TOKEN";

    static {
        INVALID_CREDENTIALS = new LoginResponse(CODE_INVALID_CREDENTIALS);
        PASSWORD_TOO_SHORT = new LoginResponse(CODE_PASSWORD_TOO_SHORT);
        PASSWORDS_DONT_MATCH = new LoginResponse(CODE_PASSWORDS_DONT_MATCH);
        INVALID_RESET_PASSWORD_TOKEN = new LoginResponse(CODE_INVALID_RESET_PASSWORD_TOKEN);
    }

    private String token;
    private Set<String> roles;
    private String personId;
    private String result;

    private LoginResponse(String result) {
        this.result = result;
    }

    public LoginResponse(String token, String personId, Set<String> roles) {
        this.token = token;
        this.roles = roles;
        this.result = CODE_SUCCESS;
        this.personId = personId;
    }
}
