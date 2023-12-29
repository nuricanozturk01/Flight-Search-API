package nuricanozturk.dev.service.flight.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class RandomConfig
{
    @Bean
    @Scope("prototype")
    public Random provideRandom()
    {
        return new SecureRandom();
    }
}
