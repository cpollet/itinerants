package net.cpollet.itinerants.core.service;

import net.cpollet.itinerants.core.domain.Person;

/**
 * Created by cpollet on 14.05.17.
 */
public interface PasswordService {
    String generateResetToken(String username);

    Person findPerson(String token);
}
