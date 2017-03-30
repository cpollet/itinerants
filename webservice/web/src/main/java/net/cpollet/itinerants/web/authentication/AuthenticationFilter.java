package net.cpollet.itinerants.web.authentication;

import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by cpollet on 15.03.17.
 */
@Log4j
public class AuthenticationFilter extends GenericFilterBean {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(servletRequest);
        HttpServletResponse httpResponse = asHttp(servletResponse);

        try {
            Optional<String> token = Optional.ofNullable(httpRequest.getHeader("X-Auth-Token"));
            if (token.isPresent()) {
                logger.debug("Trying to authenticate user by X-Auth-Token method. Token: " + token);
                processTokenAuthentication(token.get());
            }
            logger.debug("AuthenticationFilter is passing request down the filter chain");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    private void processTokenAuthentication(String token) {
        SecurityContextHolder.getContext().setAuthentication(tryToAuthenticateWithToken(token));
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        return tryToAuthenticate(new PreAuthenticatedAuthenticationToken(token, null));
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }
}
