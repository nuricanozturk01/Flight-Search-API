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

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "Authorization")
public class AuthenticationController
{
    private final IAuthenticationService m_authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService)
    {
        m_authenticationService = authenticationService;
    }

    /**
     * Login
     *
     * @param loginDTO is loginDTO
     * @return if success LoginResponseDTO else return Error Message
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO)
    {
        return subscribe(() -> ok(m_authenticationService.login(loginDTO)),
                ex -> internalServerError().body(new LoginResponseDTO(false, ex.getMessage(), null)));
    }

    /**
     * Register
     *
     * @param registerResponseDTO is registerResponseDTO
     * @return if success RegisterResponseDTO else return Error Message
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerResponseDTO)
    {
        return subscribe(() -> ok(m_authenticationService.register(registerResponseDTO)),
                ex -> internalServerError().body(new RegisterResponseDTO(false, ex.getMessage(), null)));
    }
}
