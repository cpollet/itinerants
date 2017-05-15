package net.cpollet.itinerants.core.service;

/**
 * Created by cpollet on 14.05.17.
 */
public interface PasswordService {
    String generateResetToken(String username);
}
