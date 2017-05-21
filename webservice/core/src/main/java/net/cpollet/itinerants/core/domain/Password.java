package net.cpollet.itinerants.core.domain;

/**
 * Created by cpollet on 29.03.17.
 */
public class Password {
    private final String hashedPassword;
    private final PasswordHashingService passwordHashingService;

    public Password(String hashedPassword, PasswordHashingService passwordHashingService) {
        this.hashedPassword = hashedPassword;
        this.passwordHashingService = passwordHashingService;
    }

    public boolean matches(String clearPassword) {
        String salt = passwordHashingService.extractSalt(hashedPassword);
        String hashedPassword = passwordHashingService.hash(clearPassword, salt);

        return this.hashedPassword.equals(hashedPassword);
    }

    @Override
    public String toString() {
        return hashedPassword;
    }

    public interface PasswordHashingService {
        String hash(String password);

        String hash(String password, String salt);

        String extractSalt(String hashedPassword);
    }

    public interface Factory {
        Password create(String hashedPassword);

        Password hash(String password);
    }
}
