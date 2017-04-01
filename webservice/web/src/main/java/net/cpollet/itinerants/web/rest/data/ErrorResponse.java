package net.cpollet.itinerants.web.rest.data;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by cpollet on 01.04.17.
 */
@Data
public class ErrorResponse {
    private final int status;
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
    private final String developerMessage;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.code = httpStatus.value();
        this.message = message;
        this.developerMessage = message;
    }
}
