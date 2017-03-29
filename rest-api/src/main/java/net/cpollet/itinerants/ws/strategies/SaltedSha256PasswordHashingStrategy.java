package net.cpollet.itinerants.ws.strategies;

import com.google.common.hash.Hashing;
import net.cpollet.itinerants.ws.domain.Password;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by cpollet on 29.03.17.
 */
public class SaltedSha256PasswordHashingStrategy implements Password.PasswordHashingStrategy {
    private static final String SEPARATOR = "!";

    @Override
    public String hash(String password) {
        return hash(password, generateSalt());
    }

    private String generateSalt() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public String hash(String password, String salt) {
        return salt + SEPARATOR + doHash(salt + SEPARATOR + password);
    }

    private String doHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public String extractSalt(String hashedPassword) {
        return hashedPassword.split(SEPARATOR)[0];
    }
}
