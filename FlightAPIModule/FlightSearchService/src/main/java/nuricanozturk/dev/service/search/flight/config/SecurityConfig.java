package nuricanozturk.dev.service.search.flight.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * SecurityConfig configures the security settings for the flight management system.
 * This configuration sets up JWT-based authentication, authorization rules, and other security-related settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
{
    private final JwtAuthFilter m_jwtAuthFilter;
    private final FlightAuthenticationProvider m_authenticationProvider;

    /**
     * Constructs a SecurityConfig with the necessary JWT authentication filter and authentication provider.
     *
     * @param jwtAuthFilter          The JWT authentication filter for processing HTTP request tokens.
     * @param authenticationProvider The custom authentication provider for user authentication.
     */
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, FlightAuthenticationProvider authenticationProvider)
    {
        m_jwtAuthFilter = jwtAuthFilter;
        m_authenticationProvider = authenticationProvider;
    }

    /**
     * Configures the security filter chain.
     * Sets up CSRF protection, session management, form login, and HTTP basic authentication.
     * Configures authorization rules for various endpoints.
     *
     * @param security The HttpSecurity to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception
    {
        security.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::authorizationRequests)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(m_authenticationProvider)
                .addFilterBefore(m_jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

    /**
     * Creates an AuthenticationManager bean from the AuthenticationConfiguration.
     * The AuthenticationManager is used for processing authentication requests.
     *
     * @param authenticationConfiguration The authentication configuration to derive the AuthenticationManager.
     * @return The configured AuthenticationManager.
     * @throws Exception if an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Configures authorization rules for HTTP requests.
     * Specifies which roles can access different parts of the application.
     *
     * @param requests The AuthorizationManagerRequestMatcherRegistry to set up authorization rules.
     */
    private void authorizationRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requests)
    {
        requests
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                //.requestMatchers("/dev/h2/**").permitAll() // For production
                .requestMatchers(antMatcher("/api-docs/**")).permitAll()
                .requestMatchers("/api/flight/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN");
    }
}