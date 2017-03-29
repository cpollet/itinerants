package net.cpollet.itinerants.ws.domain;

/**
 * Created by cpollet on 29.03.17.
 */
public class Password {
    private final String password;
    private final PasswordHashingStrategy passwordHashingStrategy;

    public Password(String password, PasswordHashingStrategy passwordHashingStrategy) {
        this.password = password;
        this.passwordHashingStrategy = passwordHashingStrategy;
    }

    public boolean matches(String clearPassword) {
        String salt = passwordHashingStrategy.extractSalt(password);
        String hashedPassword = passwordHashingStrategy.hash(clearPassword, salt);

        return password.equals(hashedPassword);
    }

    @Override
    public String toString() {
        return password;
    }

    public interface PasswordHashingStrategy {
        String hash(String password);

        String hash(String password, String salt);

        String extractSalt(String hashedPassword);
    }

    public interface Factory {
        Password create(String hashedPassword);

        Password hash(String password);
    }
}
