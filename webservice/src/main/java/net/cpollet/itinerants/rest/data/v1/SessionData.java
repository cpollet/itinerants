package net.cpollet.itinerants.rest.data.v1;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class SessionData {
    public String token;
    public boolean isValid;
    public List<String> rights;
}
