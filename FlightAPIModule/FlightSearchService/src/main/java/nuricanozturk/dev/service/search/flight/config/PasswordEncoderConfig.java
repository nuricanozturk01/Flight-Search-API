package nuricanozturk.dev.service.search.flight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfig provides a bean configuration for password encoding.
 * It defines a BCryptPasswordEncoder as the password encoding mechanism.
 */
@Configuration
public class PasswordEncoderConfig
{
    /**
     * Provides a PasswordEncoder bean that is used for encoding passwords.
     * The BCryptPasswordEncoder is used for its strength and security.
     *
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder providePasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}