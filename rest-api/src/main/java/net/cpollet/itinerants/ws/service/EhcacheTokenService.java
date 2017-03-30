package net.cpollet.itinerants.ws.service;

import net.cpollet.itinerants.ws.authentication.TokenService;
import org.ehcache.Cache;
import org.springframework.security.core.Authentication;

/**
 * Created by cpollet on 30.03.17.
 */
public class EhcacheTokenService implements TokenService {
    private final Cache<String, Authentication> tokenCache;

    public EhcacheTokenService(Cache<String, Authentication> tokenCache) {
        this.tokenCache = tokenCache;
    }

    @Override
    public void store(String token, Authentication authentication) {
        if (!authentication.getPrincipal().equals("expires")) {
            tokenCache.put(token, authentication);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        return tokenCache.containsKey(token);
    }

    @Override
    public Authentication retrieve(String token) {
        return tokenCache.get(token);
    }
}
