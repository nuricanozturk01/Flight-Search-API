package nuricanozturk.dev.service.search.flight.controller;

import nuricanozturk.dev.service.search.flight.DataProvider;
import nuricanozturk.dev.service.search.flight.dto.LoginDTO;
import nuricanozturk.dev.service.search.flight.dto.LoginResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;
import nuricanozturk.dev.service.search.flight.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest
{
    @Mock
    private AuthenticationService m_authenticationService;
    @InjectMocks
    private AuthenticationController m_authenticationController;
    private RegisterDTO m_customerRegisterDTO; // For using user information

    @BeforeEach
    public void setUp()
    {
        m_customerRegisterDTO = DataProvider.createCustomer();
    }

    @Test
    public void testUserAuthenticate_withGivenValidUsernameAndPassword_shouldBeSuccess()
    {
        //given
        var request = new LoginDTO(m_customerRegisterDTO.getUsername(), m_customerRegisterDTO.getPassword());
        when(m_authenticationService.login(any(LoginDTO.class)))
                .thenReturn(new LoginResponseDTO(true, "success!", "token"));

        // Act
        ResponseEntity<Object> response = m_authenticationController.login(request);


        // Assert
        var result = (LoginResponseDTO) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", result.token());
        assertTrue(result.status());
        assertEquals("success!", result.message());
    }

    @Test
    public void testAuthenticate_whenInvalidAuthenticationRequest_thenReturnUnSuccess()
    {
        //given
        var request = new LoginDTO("2143243", "password");

        when(m_authenticationService.login(any(LoginDTO.class)))
                .thenReturn(new LoginResponseDTO(false, "fail!", null));

        // Act
        ResponseEntity<Object> response = m_authenticationController.login(request);


        // Assert
        var result = (LoginResponseDTO) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(result.token());
        assertFalse(result.status());
        assertEquals("fail!", result.message());
    }
}
