package net.cpollet.itinerants.web.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.service.PasswordService;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by cpollet on 14.05.17.
 */
@Service
@Slf4j
public class EhcachePasswordService implements PasswordService {
    private final Cache<String, String> passwordResetTokenCache;
    private final Password.PasswordHashingService passwordHashingService;

    public EhcachePasswordService(Cache<String, String> passwordResetTokenCache, Password.PasswordHashingService passwordHashingService) {
        this.passwordResetTokenCache = passwordResetTokenCache;
        this.passwordHashingService = passwordHashingService;
    }

    @Override
    public String generateResetToken(String username) {
        String token = UUID.randomUUID().toString();
        String hashedToken = passwordHashingService.hash(token);

        passwordResetTokenCache.put(hashedToken, username);
        log.info("Password reset hashed token for username '{}' starts with '{}'", username, hashedToken.substring(0, 5));

        return token;
    }
}
