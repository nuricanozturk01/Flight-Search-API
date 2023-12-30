package nuricanozturk.dev.service.search.flight.config;

import nuricanozturk.dev.data.flight.repository.ICustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * FlightAuthenticationProvider is a custom implementation of AuthenticationProvider.
 * It is used for authenticating users in the flight management system using username and password.
 */
@Component
public class FlightAuthenticationProvider implements AuthenticationProvider
{
    private final PasswordEncoder passwordEncoder;
    private final ICustomerRepository m_customerRepository;

    /**
     * Constructs a FlightAuthenticationProvider with a password encoder and customer repository.
     *
     * @param passwordEncoder    The encoder used for password verification.
     * @param customerRepository The repository used for retrieving customer data.
     */
    public FlightAuthenticationProvider(PasswordEncoder passwordEncoder, ICustomerRepository customerRepository)
    {
        this.passwordEncoder = passwordEncoder;
        m_customerRepository = customerRepository;
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param authentication The authentication request object.
     * @return A fully populated authentication token for authenticated users.
     * @throws AuthenticationException if authentication fails.
     */
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

            else throw new BadCredentialsException("Invalid username or password!");
        }

        throw new BadCredentialsException("No user registered with this details!");
    }

    /**
     * Indicates whether this provider supports a given authentication type.
     *
     * @param authentication The class of the authentication request.
     * @return true if the provider supports the specified authentication type.
     */
    @Override
    public boolean supports(Class<?> authentication)
    {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}