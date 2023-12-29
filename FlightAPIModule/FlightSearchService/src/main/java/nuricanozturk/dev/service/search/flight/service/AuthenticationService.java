package nuricanozturk.dev.service.search.flight.service;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;
import static java.lang.String.format;

@Service
public class AuthenticationService implements IAuthenticationService
{
    private final FlightAuthenticationProvider m_authenticationProvider;
    private final PasswordEncoder m_passwordEncoder;
    private final ICustomerRepository m_customerRepository;
    private final IUserRegisterMapper m_userRegisterMapper;

    public AuthenticationService(FlightAuthenticationProvider authenticationProvider, PasswordEncoder passwordEncoder, ICustomerRepository customerRepository, IUserRegisterMapper userRegisterMapper)
    {
        m_authenticationProvider = authenticationProvider;
        m_passwordEncoder = passwordEncoder;
        m_customerRepository = customerRepository;
        m_userRegisterMapper = userRegisterMapper;
    }

    public Optional<Customer> findCustomerByUsername(String username)
    {
        return m_customerRepository.findByUsername(username);
    }


    @Override
    public RegisterResponseDTO register(RegisterDTO registerDTO)
    {
        return doForDataService(() -> registerCallback(registerDTO), "AuthenticationService::register");
    }


    @Override
    public LoginResponseDTO login(LoginDTO loginDTO)
    {
        return doForDataService(() -> loginCallback(loginDTO), "AuthenticationService::login");
    }

    //-----------------------------------------_CALLBACKS_---------------------------------------


    private LoginResponseDTO loginCallback(LoginDTO loginDTO)
    {
        if (loginDTO == null)
            throw new DataServiceException("AuthenticationRequest is null!");

        var auth = m_authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

        if (!auth.isAuthenticated())
            throw new DataServiceException("Invalid username or password!");

        return new LoginResponseDTO(true, "User login operation is successful", JwtUtil.generateToken(loginDTO.username()));
    }


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
