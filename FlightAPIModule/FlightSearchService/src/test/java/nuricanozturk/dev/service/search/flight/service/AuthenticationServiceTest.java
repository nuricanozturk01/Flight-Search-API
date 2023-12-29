package nuricanozturk.dev.service.search.flight.service;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.flight.entity.Customer;
import nuricanozturk.dev.service.search.flight.DatabaseCleaner;
import nuricanozturk.dev.service.search.flight.Injection;
import nuricanozturk.dev.service.search.flight.dto.LoginDTO;
import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import static nuricanozturk.dev.data.flight.util.RepositoryBeanName.AIRPORT_REPOSITORY_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.FLIGHT_SERVICE_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.TEST_PROPERTIES_FILE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EntityScan(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
@ComponentScan(basePackages = {AIRPORT_REPOSITORY_BEAN_NAME, FLIGHT_SERVICE_BEAN_NAME})
@TestPropertySource(locations = TEST_PROPERTIES_FILE)
public class AuthenticationServiceTest
{
    @Autowired
    private DatabaseCleaner m_databaseCleaner;
    @Autowired
    private Injection m_injection;
    private Customer customer;

    @BeforeEach
    public void setUpAndCheckUsers()
    {
        var userRegister = new RegisterDTO("nuricanozturk",
                "Nuri",
                "Can",
                "OZTURK",
                "canozturk309@gmail.com",
                "pass123");
        var registerResult = m_injection.getAuthenticationService().register(userRegister);

        assertNotNull(registerResult);
        assertNotNull(registerResult.token());

        var customer = m_injection.getAuthenticationService().findCustomerByUsername("nuricanozturk");

        customer.ifPresentOrElse(c -> this.customer = c, () -> fail("Customer not found!"));
    }


    @Test
    public void testLoginOperation_withGivenValidCredentials_shouldNotNull()
    {
        var loginRequest = new LoginDTO(customer.getUsername(), "pass123");
        var loginResponse = m_injection.getAuthenticationService().login(loginRequest);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.token());
        assertTrue(loginResponse.status());
    }


    @Test
    public void testLoginOperation_withGivenInvalidCredentials_shouldThrowDataServiceException()
    {
        var loginRequest = new LoginDTO("qwerty", "dsadsafs");

        var exception = assertThrows(DataServiceException.class,
                () -> m_injection.getAuthenticationService().login(loginRequest));
        assertEquals("Message: AuthenticationService::login , Cause Message:No user registered with this details!", exception.getMessage());

    }


    @Test
    public void testLoginOperation_withGivenCorrectUsernameAndWrongPassword_shouldThrowDataServiceException()
    {
        var loginRequest = new LoginDTO(customer.getUsername(), "password321");

        var exception = assertThrows(DataServiceException.class,
                () -> m_injection.getAuthenticationService().login(loginRequest));
        assertEquals("Message: AuthenticationService::login , Cause Message:Invalid username or password!", exception.getMessage());
    }

    @Test
    public void testLoginOperation_withGivenWrongUsernameAndCorrectPassword_shouldThrowDataServiceException()
    {
        var loginRequest = new LoginDTO("emirr", customer.getPassword());

        var exception = assertThrows(DataServiceException.class,
                () -> m_injection.getAuthenticationService().login(loginRequest));
        assertEquals("Message: AuthenticationService::login , Cause Message:No user registered with this details!",
                exception.getMessage());
    }


    @Test
    public void testLoginOperation_withGivenEmptyUsernameAndPassword_shouldThrowDataServiceException()
    {
        var loginRequest = new LoginDTO("", "");

        var exception = assertThrows(DataServiceException.class,
                () -> m_injection.getAuthenticationService().login(loginRequest));
        assertEquals("Message: AuthenticationService::login , Cause Message:No user registered with this details!", exception.getMessage());
    }


    @Test
    public void testLoginOperation_withGivenNullUser_shouldThrowDataServiceException()
    {

        var exception = assertThrows(DataServiceException.class,
                () -> m_injection.getAuthenticationService().login(null));
        assertEquals("Message: AuthenticationService::login , Cause Message:Message: AuthenticationRequest is null! ", exception.getMessage());
    }

    @Test
    public void testRegisterOperation_withGivenValidRegisterDTO_shouldNotNull()
    {
        var user = new RegisterDTO("John", "Doe", "M", "john.doe", "john@example.com", "password123");

        var registerResponse = m_injection.getAuthenticationService().register(user);

        assertNotNull(registerResponse);
        assertNotNull(registerResponse.token());
        assertTrue(registerResponse.status());
    }


    @AfterEach
    public void tearDown()
    {
        m_databaseCleaner.clearH2Database();
    }
}