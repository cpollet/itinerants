package net.cpollet.itinerants.ws.api.v1.data;

import lombok.Data;

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
    private String result;

    private LoginResponse() {
        // nothing to do
    }

    public LoginResponse(String token) {
        this.token = token;
        this.result = CODE_SUCCESS;
    }
}
