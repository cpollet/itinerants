package net.cpollet.itinerants.ws.authentication;

import org.ehcache.Cache;
import org.springframework.security.core.Authentication;

/**
 * Created by cpollet on 15.03.17.
 */
public class TokenService {
    private final Cache<String, Authentication> tokenCache;

    public TokenService(Cache<String, Authentication> tokenCache) {
        this.tokenCache = tokenCache;
    }

    public void store(String token, Authentication authentication) {
        if (!authentication.getPrincipal().equals("expires")) {
            tokenCache.put(token, authentication);
        }
    }

    public boolean isTokenValid(String token) {
        return tokenCache.containsKey(token);
    }

    public Authentication retrieve(String token) {
        return tokenCache.get(token);
    }
}
