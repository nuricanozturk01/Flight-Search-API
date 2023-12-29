package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.service.search.flight.dto.LoginDTO;
import nuricanozturk.dev.service.search.flight.dto.LoginResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterResponseDTO;

public interface IAuthenticationService
{
    RegisterResponseDTO register(RegisterDTO registerDTO);
    LoginResponseDTO login(LoginDTO loginDTO);
}
