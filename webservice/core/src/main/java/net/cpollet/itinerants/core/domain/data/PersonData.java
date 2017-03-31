package net.cpollet.itinerants.core.domain.data;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonData {
    Long getId();

    String getName();

    String getUsername();

    String getPassword();

    String getRoles();

    void availableFor(EventData eventData);
}
