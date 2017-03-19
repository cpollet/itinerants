package net.cpollet.itinerants.ws.authentication;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cpollet on 15.03.17.
 */
public class TokenService {
    private final Cache<String, Authentication> tokenCache;

    public TokenService(Cache<String, Authentication> tokenCache) {
        this.tokenCache = tokenCache;
    }

    public void store(String token, Authentication authentication) {
        tokenCache.put(token, authentication);
    }

    public boolean isTokenValid(String token) {
        return tokenCache.containsKey(token);
    }

    public Authentication retrieve(String token) {
        return tokenCache.get(token);
    }
}
