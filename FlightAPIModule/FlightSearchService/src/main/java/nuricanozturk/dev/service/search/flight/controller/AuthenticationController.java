package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.service.search.flight.dto.LoginDTO;
import nuricanozturk.dev.service.search.flight.dto.LoginResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterResponseDTO;
import nuricanozturk.dev.service.search.flight.service.IAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static callofproject.dev.library.exception.util.ExceptionUtil.subscribe;
import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

/**
 * AuthenticationController handles the authentication processes for the flight management system.
 * This controller provides endpoints for user login and registration.
 * It uses IAuthenticationService to process authentication and registration requests.
 */
@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "Authorization")
public class AuthenticationController
{
    private final IAuthenticationService m_authenticationService;

    /**
     * Constructs an AuthenticationController with the necessary authentication service.
     *
     * @param authenticationService The service used for handling authentication operations.
     */
    public AuthenticationController(IAuthenticationService authenticationService)
    {
        m_authenticationService = authenticationService;
    }

    /**
     * Handles the login process for a user.
     *
     * @param loginDTO The data transfer object containing the user's login credentials.
     * @return A ResponseEntity containing the login response data.
     * In case of success, it returns a successful login response;
     * in case of failure, it returns an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO)
    {
        return subscribe(() -> ok(m_authenticationService.login(loginDTO)),
                ex -> internalServerError().body(new LoginResponseDTO(false, ex.getMessage(), null)));
    }

    /**
     * Handles the registration process for a new user.
     *
     * @param registerResponseDTO The data transfer object containing the user's registration details.
     * @return A ResponseEntity containing the registration response data.
     * In case of success, it returns a successful registration response;
     * in case of failure, it returns an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerResponseDTO)
    {
        return subscribe(() -> ok(m_authenticationService.register(registerResponseDTO)),
                ex -> internalServerError().body(new RegisterResponseDTO(false, ex.getMessage(), null)));
    }
}
