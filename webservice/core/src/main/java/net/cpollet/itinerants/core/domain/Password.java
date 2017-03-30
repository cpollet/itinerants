package net.cpollet.itinerants.core.domain;

/**
 * Created by cpollet on 29.03.17.
 */
public class Password {
    private final String password;
    private final PasswordHashingService passwordHashingService;

    public Password(String password, PasswordHashingService passwordHashingService) {
        this.password = password;
        this.passwordHashingService = passwordHashingService;
    }

    public boolean matches(String clearPassword) {
        String salt = passwordHashingService.extractSalt(password);
        String hashedPassword = passwordHashingService.hash(clearPassword, salt);

        return password.equals(hashedPassword);
    }

    @Override
    public String toString() {
        return password;
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
