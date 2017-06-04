package net.cpollet.itinerants.web.rest.data;

import lombok.Data;

import java.util.List;

/**
 * Created by cpollet on 04.06.17.
 */
@Data
public class ValidationErrorResponse {
    private final List<FieldErrorResponse> errors;
}
