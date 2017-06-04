package net.cpollet.itinerants.web.rest.data;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by cpollet on 25.05.17.
 */
@Data
public class PersonProfilePayload {
    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String lastName;
    @Email
    @NotEmpty
    private final String email;
}
