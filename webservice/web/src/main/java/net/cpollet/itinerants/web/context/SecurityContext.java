package net.cpollet.itinerants.web.context;

import lombok.extern.slf4j.Slf4j;
import net.cpollet.itinerants.web.authentication.AuthenticationFilter;
import net.cpollet.itinerants.web.authentication.EhcacheTokenService;
import net.cpollet.itinerants.web.authentication.TokenAuthenticationProvider;
import net.cpollet.itinerants.web.authentication.TokenService;
import net.cpollet.itinerants.web.configuration.AuthenticationProperties;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.concurrent.TimeUnit;

/**
 * Created by cpollet on 14.03.17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityContext extends WebSecurityConfigurerAdapter {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/sessions/**").permitAll()
                .antMatchers("/people/*/passwords/*").permitAll()
                .antMatchers("/**").hasAnyRole("USER", "ADMIN");

        http
                .addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    /**
     * http://stackoverflow.com/questions/42316643/spring-security-configureauthenticationmanagerbuilder-auth-vs-authentication
     *
     * @return the {@link AuthenticationManager} instance, configured in {@link SecurityContext#configure(AuthenticationManagerBuilder)}
     * @throws Exception
     */
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenService());
    }

    @Bean
    public TokenService tokenService() {
        return new EhcacheTokenService(tokenCache());
    }

    @Bean
    public Cache<String, Authentication> tokenCache() {
        log.info("Session token timeout: {}", authenticationProperties.getResetPasswordTokenTimeout());
        CacheConfiguration<String, Authentication> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Authentication.class, ResourcePoolsBuilder.heap(100))
                .withExpiry(Expirations.timeToIdleExpiration(new Duration(authenticationProperties.getSessionTokenTimeout(), TimeUnit.MINUTES)))
                .build();

        return cacheManager.createCache("tokenCache", cacheConfiguration);
    }
}
