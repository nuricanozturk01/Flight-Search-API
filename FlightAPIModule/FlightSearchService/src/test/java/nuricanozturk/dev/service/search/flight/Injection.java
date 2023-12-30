package nuricanozturk.dev.service.search.flight;

import nuricanozturk.dev.data.flight.repository.IAirportRepository;
import nuricanozturk.dev.data.flight.repository.ICustomerRepository;
import nuricanozturk.dev.data.flight.repository.IFlightRepository;
import nuricanozturk.dev.service.search.flight.service.impl.AuthenticationService;
import nuricanozturk.dev.service.search.flight.service.impl.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
@SpringBootTest
public class Injection
{
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FlightService flightService;
    @Autowired
    private ICustomerRepository m_customerRepository;
    @Autowired
    private IFlightRepository m_flightRepository;
    @Autowired
    private IAirportRepository m_airportRepository;

    public AuthenticationService getAuthenticationService()
    {
        return authenticationService;
    }

    public FlightService getFlightService()
    {
        return flightService;
    }
}