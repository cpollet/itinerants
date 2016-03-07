package net.cpollet.itinerants.services.data;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class Session {
    private final List<String> rights;

    public Session(List<String> rights) {
        this.rights = rights;
    }

    public List<String> getRights() {
        return rights;
    }
}
