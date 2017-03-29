package net.cpollet.itinerants.ws.service.data;

/**
 * Created by cpollet on 13.02.17.
 */
public interface Person {
    Long getId();

    String getName();

    String getUsername();

    String getPassword();

    void availableFor(Event event);
}
