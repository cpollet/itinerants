package net.cpollet.itinerants.web.service;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.core.domain.Password;
import net.cpollet.itinerants.core.domain.Person;
import net.cpollet.itinerants.core.service.PasswordService;
import net.cpollet.itinerants.core.service.PersonService;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final PersonService personService;
    private final String salt;

    public EhcachePasswordService(Cache<String, String> passwordResetTokenCache,
                                  Password.PasswordHashingService passwordHashingService,
                                  PersonService personService,
                                  @Qualifier("resetPasswordTokenSalt") String salt) {
        this.passwordResetTokenCache = passwordResetTokenCache;
        this.passwordHashingService = passwordHashingService;
        this.personService = personService;
        this.salt = salt;
    }

    @Override
    public String generateResetToken(String username) {
        String token = UUID.randomUUID().toString();
        String hashedToken = passwordHashingService.hash(token, salt);

        passwordResetTokenCache.put(hashedToken, username);

        return token;
    }

    @Override
    public Person findPerson(String token) {
        String hashedToken = passwordHashingService.hash(token, salt);
        String username = passwordResetTokenCache.get(hashedToken);

        passwordResetTokenCache.remove(hashedToken);

        return personService.getByUsername(username);
    }
}
