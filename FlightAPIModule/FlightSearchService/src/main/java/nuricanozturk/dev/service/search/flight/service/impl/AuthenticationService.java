package nuricanozturk.dev.service.search.flight.service.impl;

import callofproject.dev.library.exception.service.DataServiceException;
import callofproject.dev.service.jwt.JwtUtil;
import nuricanozturk.dev.data.flight.entity.Customer;
import nuricanozturk.dev.data.flight.entity.Role;
import nuricanozturk.dev.data.flight.repository.ICustomerRepository;
import nuricanozturk.dev.service.search.flight.config.FlightAuthenticationProvider;
import nuricanozturk.dev.service.search.flight.dto.LoginDTO;
import nuricanozturk.dev.service.search.flight.dto.LoginResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterResponseDTO;
import nuricanozturk.dev.service.search.flight.mapper.IUserRegisterMapper;
import nuricanozturk.dev.service.search.flight.service.IAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;
import static java.lang.String.format;

/**
 * AuthenticationService handles the authentication processes for the flight management system.
 * This service includes operations for customer registration and login.
 * It utilizes a custom authentication provider and a password encoder for security.
 */
@Service
public class AuthenticationService implements IAuthenticationService
{
    private final FlightAuthenticationProvider m_authenticationProvider;
    private final PasswordEncoder m_passwordEncoder;
    private final ICustomerRepository m_customerRepository;
    private final IUserRegisterMapper m_userRegisterMapper;

    /**
     * Constructs an AuthenticationService with the necessary dependencies.
     *
     * @param authenticationProvider The provider used for authentication processes.
     * @param passwordEncoder        The encoder used for password hashing.
     * @param customerRepository     The repository for customer data access.
     * @param userRegisterMapper     The mapper for converting register DTOs to customer entities.
     */
    public AuthenticationService(FlightAuthenticationProvider authenticationProvider, PasswordEncoder passwordEncoder, ICustomerRepository customerRepository, IUserRegisterMapper userRegisterMapper)
    {
        m_authenticationProvider = authenticationProvider;
        m_passwordEncoder = passwordEncoder;
        m_customerRepository = customerRepository;
        m_userRegisterMapper = userRegisterMapper;
    }

    /**
     * Finds a customer by their username.
     *
     * @param username The username of the customer.
     * @return An Optional containing the found customer or empty if not found.
     */
    public Optional<Customer> findCustomerByUsername(String username)
    {
        return m_customerRepository.findByUsername(username);
    }

    /**
     * Registers a new customer into the system.
     *
     * @param registerDTO The data transfer object containing registration information.
     * @return RegisterResponseDTO containing registration status and details.
     */
    @Override
    public RegisterResponseDTO register(RegisterDTO registerDTO)
    {
        return doForDataService(() -> registerCallback(registerDTO), "AuthenticationService::register");
    }

    /**
     * Logs in a customer into the system.
     *
     * @param loginDTO The data transfer object containing login credentials.
     * @return LoginResponseDTO containing login status and details.
     */
    @Override
    public LoginResponseDTO login(LoginDTO loginDTO)
    {
        return doForDataService(() -> loginCallback(loginDTO), "AuthenticationService::login");
    }

    //-----------------------------------------_CALLBACKS_---------------------------------------

    /**
     * Callback method for handling login logic.
     *
     * @param loginDTO The data transfer object containing login credentials.
     * @return LoginResponseDTO containing the outcome of the login process.
     * @throws DataServiceException if the login request is null or authentication fails.
     */
    private LoginResponseDTO loginCallback(LoginDTO loginDTO)
    {
        if (loginDTO == null)
            throw new DataServiceException("AuthenticationRequest is null!");

        var auth = m_authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

        if (!auth.isAuthenticated())
            throw new DataServiceException("Invalid username or password!");


        return new LoginResponseDTO(true, "User login operation is successful", JwtUtil.generateToken(loginDTO.username()));
    }

    /**
     * Callback method for handling registration logic.
     *
     * @param registerDTO The data transfer object containing registration information.
     * @return RegisterResponseDTO containing the outcome of the registration process.
     */
    private RegisterResponseDTO registerCallback(RegisterDTO registerDTO)
    {
        registerDTO.setPassword(m_passwordEncoder.encode(registerDTO.getPassword()));
        var user = m_userRegisterMapper.toCustomer(registerDTO);
        user.addRole(Role.ROLE_USER);
        var savedUser = m_customerRepository.save(user);

        var token = JwtUtil.generateToken(savedUser.getUsername());

        return new RegisterResponseDTO(true, format("Welcome %s!", savedUser.getUsername()), token);
    }
}
