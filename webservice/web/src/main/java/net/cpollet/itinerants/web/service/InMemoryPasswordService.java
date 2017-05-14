package net.cpollet.itinerants.web.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.service.PasswordService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by cpollet on 14.05.17.
 */
@Service
@Slf4j
public class InMemoryPasswordService implements PasswordService{
    private final Password.PasswordHashingService passwordHashingService;

    public InMemoryPasswordService(Password.PasswordHashingService passwordHashingService) {
        this.passwordHashingService = passwordHashingService;
    }

    @Override
    public String reset(String username) {
        String token = UUID.randomUUID().toString();
        String hashedToken = passwordHashingService.hash(token);
        log.info("Password reset token for {} is {}", username, hashedToken);
        return token;
    }
}
