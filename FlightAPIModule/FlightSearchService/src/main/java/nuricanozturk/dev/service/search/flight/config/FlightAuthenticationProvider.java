package nuricanozturk.dev.service.search.flight.config;

import nuricanozturk.dev.data.flight.repository.ICustomerRepository;
import nuricanozturk.dev.service.search.flight.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FlightAuthenticationProvider implements AuthenticationProvider
{

    private final PasswordEncoder passwordEncoder;

    private final ICustomerRepository m_customerRepository;

    public FlightAuthenticationProvider(PasswordEncoder passwordEncoder, ICustomerRepository customerRepository)
    {
        this.passwordEncoder = passwordEncoder;
        m_customerRepository = customerRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        var username = authentication.getName();
        var pwd = authentication.getCredentials().toString();
        var user = m_customerRepository.findByUsername(username);

        if (user.isPresent())
        {
            if (passwordEncoder.matches(pwd, user.get().getPassword()))
                return new UsernamePasswordAuthenticationToken(username, pwd, user.get().getRoles());

            else throw new BadCredentialsException("Invalid password!");
        } else throw new BadCredentialsException("No user registered with this details!");
    }


    @Override
    public boolean supports(Class<?> authentication)
    {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}