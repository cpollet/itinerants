package net.cpollet.itinerants.core.domain.data;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonData {
    String getUUID();

    String getFirstName();

    String getLastName();

    String getUsername();

    String getPassword();

    String getEmail();

    String getRoles();

    float getTargetRatio();

    void availableFor(EventData eventData);
}
