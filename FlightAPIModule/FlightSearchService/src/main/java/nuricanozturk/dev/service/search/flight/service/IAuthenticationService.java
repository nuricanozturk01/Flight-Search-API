package nuricanozturk.dev.service.search.flight.service;


import nuricanozturk.dev.data.common.dto.LoginDTO;
import nuricanozturk.dev.data.common.dto.LoginResponseDTO;
import nuricanozturk.dev.data.common.dto.RegisterDTO;
import nuricanozturk.dev.data.common.dto.RegisterResponseDTO;

/**
 * IAuthenticationService interface handles the authentication processes for the flight management system.
 * This service includes operations for customer registration and login.
 * It utilizes a custom authentication provider and a password encoder for security.
 */
public interface IAuthenticationService
{
    /**
     * Registers a new customer into the system.
     *
     * @param registerDTO The data transfer object containing registration information.
     * @return RegisterResponseDTO containing registration status and details.
     */
    RegisterResponseDTO register(RegisterDTO registerDTO);

    /**
     * Logs in a customer into the system.
     *
     * @param loginDTO The data transfer object containing login credentials.
     * @return LoginResponseDTO containing login status and details.
     */
    LoginResponseDTO login(LoginDTO loginDTO);
}
