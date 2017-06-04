package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

/**
 * Created by cpollet on 04.06.17.
 */
@Data
public class FieldErrorResponse {
    private final String field;
    private final String message;
}
