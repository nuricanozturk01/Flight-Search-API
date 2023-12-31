package nuricanozturk.dev.service.search.flight.service;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.common.dto.FlightInfoDTO;
import nuricanozturk.dev.data.common.dto.request.CreateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.CreateFlightDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Customer;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.data.flight.entity.Role;
import nuricanozturk.dev.service.search.flight.DatabaseCleaner;
import nuricanozturk.dev.service.search.flight.Injection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static java.util.Optional.empty;
import static nuricanozturk.dev.data.flight.util.RepositoryBeanName.AIRPORT_REPOSITORY_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.DataProvider.*;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.FLIGHT_SERVICE_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.TEST_PROPERTIES_FILE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EntityScan(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
@ComponentScan(basePackages = {AIRPORT_REPOSITORY_BEAN_NAME, FLIGHT_SERVICE_BEAN_NAME})
@TestPropertySource(locations = TEST_PROPERTIES_FILE)
public class AdminServiceTest
{
    @Autowired
    private DatabaseCleaner m_databaseCleaner;
    @Autowired
    private Injection m_injection;
    private Customer customer;
    private Flight oneWayFlight;
    private Flight roundTrapFlight;
    private Airport istanbulAirport;
    private Airport ankaraAirport;
    @Autowired
    private PasswordEncoder m_passwordEncoder;

    @BeforeEach
    public void setUpAndCheckUsers()
    {

        var user = new Customer("ozturkcan",
                m_passwordEncoder.encode("pass123"),
                "Nuri",
                "Can",
                "OZTURK",
                "asdfg@gmail.com",
                Role.ROLE_ADMIN);

        customer = m_injection.getCustomerRepository().save(user);
        //Save Airports
        istanbulAirport = m_injection.getAirportRepository().save(provideIstanbulAirport());
        ankaraAirport = m_injection.getAirportRepository().save(provideAnkaraAirport());
        //save flights
        oneWayFlight = m_injection.getFlightRepository().save(provideOneWayFlight(istanbulAirport, ankaraAirport));
        var roundTrapFlightObj = provideRoundTrapFlight(istanbulAirport, ankaraAirport);
        m_injection.getFlightRepository().save(roundTrapFlightObj.getReturnFlight());
        roundTrapFlight = m_injection.getFlightRepository().save(roundTrapFlightObj);
    }

    @Test
    public void createFlight_withGivenCreateFlightDTO_shouldBeSaved()
    {
        var flight = new CreateFlightDTO("IZMIR", "ANKARA", LocalDate.now(),
                LocalTime.now(), empty(), empty(), empty(), 100);
        var createdFlight = m_injection.getAdminService().createFlight(flight);
        assertNotNull(createdFlight);
        assertNotNull(createdFlight.data());
        assertNotNull(((FlightInfoDTO) createdFlight.data()));
    }

    @Test
    public void createFlight_withGivenCreateFlightDTO_shouldNotBeSaved()
    {
        var flight = new CreateFlightDTO(null, "ANKARA", LocalDate.now(),
                LocalTime.now(), empty(), empty(), empty(), 100);
        var exception = assertThrows(DataServiceException.class, () -> m_injection.getAdminService().createFlight(flight));
        assertNotNull(exception);
        assertEquals("Message: Arrival Airport is required! ", exception.getMessage());
    }


    @Test
    public void createAirport_withGivenAirportName_shouldBeSaved()
    {
        var airport = new CreateAirportDTO("EDIRNE");
        var createdFlight = m_injection.getAdminService().createAirport(airport);
        assertNotNull(createdFlight);
        assertNotNull(createdFlight.data());
    }

    @Test
    public void createAirport_withGivenAirportName_shouldNotBeSaved()
    {
        var exception = assertThrows(DataServiceException.class,
                () -> m_injection.getAdminService().createAirport(new CreateAirportDTO(null)));

        assertNotNull(exception);
        assertEquals("Message: City is required! ", exception.getMessage());
    }


    @Test
    public void updateFlight_withGivenUpdateFlightDTO_shouldBeUpdated()
    {
        var flight = new UpdateFlightDTO(oneWayFlight.getId(), "ANTALYA", "ANKARA", LocalDate.now(),
                LocalTime.now(), empty(), empty(), 900);

        var createdFlight = m_injection.getAdminService().updateFlight(flight);
        assertNotNull(createdFlight);
        assertNotNull(createdFlight.data());

        var updatedFlight = (Flight) createdFlight.data();
        assertNotNull(updatedFlight);
        assertEquals("ANKARA", updatedFlight.getDepartureAirport().getCity());
        assertEquals("ANTALYA", updatedFlight.getArrivalAirport().getCity());
        assertEquals(900, updatedFlight.getPrice());
        assertEquals(oneWayFlight.getId(), updatedFlight.getId());
    }

    @Test
    public void updateFlight_withGivenUpdateFlightDTO_shouldNotBeUpdated()
    {
        var flight = new UpdateFlightDTO(oneWayFlight.getId(), "ANTALYA", null, LocalDate.now(),
                LocalTime.now(), empty(), empty(), 900);
        var exception = assertThrows(DataServiceException.class, () -> m_injection.getAdminService().updateFlight(flight));
        assertNotNull(exception);
        assertEquals("Message: Departure Airport is required! ", exception.getMessage());
    }

    @Test
    public void updateAirport_withGivenUpdateAirportDTO_shouldBeUpdated()
    {
        var airport = new UpdateAirportDTO(istanbulAirport.getId(), "ANTALYA");

        var updatedAirportResponse = m_injection.getAdminService().updateAirport(airport);
        assertNotNull(updatedAirportResponse);
        assertNotNull(updatedAirportResponse.data());

        var updatedAirport = (Airport) updatedAirportResponse.data();
        assertNotNull(updatedAirport);
        assertEquals("ANTALYA", updatedAirport.getCity());
        assertEquals(istanbulAirport.getId(), updatedAirport.getId());
    }

    @Test
    public void updateAirport_withGivenUpdateAirportDTO_shouldNotBeUpdated()
    {
        var airport = new UpdateAirportDTO(istanbulAirport.getId(), null);
        var exception = assertThrows(DataServiceException.class, () -> m_injection.getAdminService().updateAirport(airport));
        assertNotNull(exception);
        assertEquals("Message: City is required! ", exception.getMessage());
    }


    @Test
    public void deleteFlight_withGivenFlightId_shouldBeRemoved()
    {
        var id = oneWayFlight.getId();
        System.out.println("nuricanozturk-id: " + id);
        var removedAirport = m_injection.getAdminService().deleteFlightById(id);
        assertNotNull(removedAirport);
        assertNotNull(removedAirport.data());

        var updatedAirport = (String) removedAirport.data();
        assertNotNull(updatedAirport);
        assertEquals(id.toString() + " removed successfully!", updatedAirport);

        var findRemovedFlight = m_injection.getFlightRepository().findById(id);
        assertNotNull(findRemovedFlight);
        assertTrue(findRemovedFlight.isEmpty());
    }

    @Test
    public void deleteFlight_withGivenFlightId_shouldThrowDataServiceException()
    {
        var id = UUID.randomUUID();
        var exception = assertThrows(DataServiceException.class, () -> m_injection.getAdminService().deleteFlightById(id));
        assertNotNull(exception);
        assertEquals("Message: Flight Not Found! ", exception.getMessage());
    }


    @Test
    public void deleteAirport_withGivenAirportId_shouldBeRemoved()
    {
        var createAirport = m_injection.getAirportRepository().save(new Airport("MUGLA"));

        var removedAirport = m_injection.getAdminService().deleteAirportByCityName(createAirport.getCity());
        assertNotNull(removedAirport);
        assertNotNull(removedAirport.data());

        var updatedAirport = (String) removedAirport.data();
        assertNotNull(updatedAirport);
        assertEquals(createAirport.getId().toString() + " removed successfully!", updatedAirport);

        var findRemovedAirport = m_injection.getAirportRepository().findByCity(createAirport.getCity());
        assertNotNull(findRemovedAirport);
        assertTrue(findRemovedAirport.isEmpty());
    }

    @Test
    public void deleteAirport_withGivenAirportId_shouldThrowDataServiceException()
    {
        var exception = assertThrows(DataServiceException.class, () -> m_injection.getAdminService().deleteAirportByCityName("NOT_CITY"));
        assertNotNull(exception);
        assertEquals("Message: Airport Not Found! ", exception.getMessage());
    }

    @AfterEach
    public void tearDown()
    {
        m_databaseCleaner.clearH2Database();
    }
}
