package net.cpollet.itinerants.web.context;

import net.cpollet.itinerants.web.authentication.AuthenticationFilter;
import net.cpollet.itinerants.web.authentication.EhcacheTokenService;
import net.cpollet.itinerants.web.authentication.TokenAuthenticationProvider;
import net.cpollet.itinerants.web.authentication.TokenService;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
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
public class SecurityContext extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/sessions/**").permitAll()
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
        return tokenCacheManager().getCache("tokenCache", String.class, Authentication.class);
    }

    private CacheConfiguration<String, Authentication> tokenCacheConfiguration() {
        return CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Authentication.class, ResourcePoolsBuilder.heap(100))
                .withExpiry(Expirations.timeToIdleExpiration(new Duration(15, TimeUnit.HOURS)))
                .build();
    }

    private CacheManager tokenCacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("tokenCache", tokenCacheConfiguration())
                .build(true);
    }
}
