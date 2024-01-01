package nuricanozturk.dev.service.search.flight.service;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.common.dto.FlightsResponseDTO;
import nuricanozturk.dev.data.common.dto.request.*;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.DatabaseCleaner;
import nuricanozturk.dev.service.search.flight.Injection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.IntStream.range;
import static nuricanozturk.dev.data.flight.util.RepositoryBeanName.AIRPORT_REPOSITORY_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.DataProvider.*;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.FLIGHT_SERVICE_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.TEST_PROPERTIES_FILE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EntityScan(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
@ComponentScan(basePackages = {AIRPORT_REPOSITORY_BEAN_NAME, FLIGHT_SERVICE_BEAN_NAME})
@TestPropertySource(locations = TEST_PROPERTIES_FILE)
public class FlightServiceTest
{
    @Autowired
    private DatabaseCleaner m_databaseCleaner;
    @Autowired
    private Injection m_injection;
    private Flight oneWayFlight;
    private Flight roundTrapFlight;
    private Airport istanbulAirport;
    private Airport ankaraAirport;


    @BeforeEach
    public void setUpAndCheckUsers()
    {
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
    public void findFlightById_existingId_shouldReturnFlight()
    {
        var result = m_injection.getFlightService().findFlightById(oneWayFlight.getId());
        assertTrue(result.isPresent());
    }

    @Test
    public void findFlightById_nonExistingId_shouldReturnEmpty()
    {
        var result = m_injection.getFlightService().findFlightById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    public void findFlightsByArrivalAirport_validAirport_shouldReturnFlights()
    {
        var result = m_injection.getFlightService().findFlightsByArrivalAirport(ankaraAirport.getCity(), 1);
        assertNotNull(result);
    }

    @Test
    public void findFlightsByArrivalAirport_withInvalidAirport_shouldReturnEmptyList()
    {
        var result = m_injection.getFlightService().findFlightsByArrivalAirport("new york", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertTrue(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByDepartureAirport_validAirport_shouldReturnFlights()
    {
        var result = m_injection.getFlightService().findFlightsByDepartureAirport(istanbulAirport.getCity(), 1);
        assertNotNull(result);
    }

    @Test
    public void findFlightsByDepartureAirport_withInvalidAirport_shouldReturnEmptyList()
    {
        var result = m_injection.getFlightService().findFlightsByArrivalAirport("new york", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertTrue(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByArrivalAirportAndDepartureAirport_validAirports_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findFlightsByArrivalAirportAndDepartureAirport(istanbulAirport.getCity(), ankaraAirport.getCity(), 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByArrivalAirportAndDepartureAirport_withInvalidAirports_shouldReturnEmptyList()
    {
        var result = m_injection.getFlightService()
                .findFlightsByArrivalAirportAndDepartureAirport("los santos", "miami", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertTrue(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByFromAndToLocationAndDate_validInputs_shouldReturnFlights()
    {
        var searchDTO = new SearchFullQualifiedDTO(istanbulAirport.getCity(), ankaraAirport.getCity(),
                LocalDate.of(2024, 2, 1),
                Optional.of(LocalDate.of(2024, 2, 15)),
                1);

        var result = m_injection.getFlightService().findFlightsByFromAndToLocationAndDate(searchDTO);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByFromAndToLocationAndDate_withGivenInvalidReturnDate_shouldThrowDataServiceException()
    {
        var searchDTO = new SearchFullQualifiedDTO(istanbulAirport.getCity(), ankaraAirport.getCity(),
                LocalDate.of(2024, 2, 1),
                Optional.empty(),
                1);

        var result = assertThrows(DataServiceException.class, () -> m_injection.getFlightService().findFlightsByFromAndToLocationAndDate(searchDTO));
        assertNotNull(result);
        assertEquals("Message: Return date cannot be empty ", result.getMessage());
    }

    @Test
    public void findFlightsByFromAndToLocationAndDate_withGivenInvalidDateRangeDate_shouldThrowDataServiceException()
    {
        var searchDTO = new SearchFullQualifiedDTO(istanbulAirport.getCity(), ankaraAirport.getCity(),
                LocalDate.of(2024, 2, 15),
                Optional.of(LocalDate.of(2024, 2, 1)),
                1);

        var result = assertThrows(DataServiceException.class, () -> m_injection.getFlightService().findFlightsByFromAndToLocationAndDate(searchDTO));
        assertNotNull(result);
        assertEquals("Message: Departure date cannot be after return date ", result.getMessage());
    }

    @Test
    public void findFlightsByFromAndToAndDateBetween_withGivenValidInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService().
                findFlightsByFromAndToAndDateBetween(istanbulAirport.getCity(), ankaraAirport.getCity(), "01/02/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByFromAndToAndDateBetween_withGivenInValidInputs_shouldThrowDataServiceException()
    {
        var result = assertThrows(DataServiceException.class, () -> m_injection.getFlightService()
                .findFlightsByFromAndToAndDateBetween(istanbulAirport.getCity(), ankaraAirport.getCity(), "01/23/2024", 1));
        assertNotNull(result);
        var msg = "Message: FlightService::findFlightsByFromAndToAndDateBetween , Cause Message:Message: FlightService::parseDate , Cause Message:Text '01/23/2024' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 23";
        assertEquals(msg, result.getMessage());
    }

    @Test
    public void findFlightsByFromAndToAndDateBetween_validDateRange_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findFlightsByFromAndToAndDateBetween(istanbulAirport.getCity(), ankaraAirport.getCity(), "01/02/2024", "20/02/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByFromAndToAndDateBetween_validDateRange_shouldReturnEmptyList()
    {
        // Flights not found
        var result = m_injection.getFlightService()
                .findFlightsByFromAndToAndDateBetween(istanbulAirport.getCity(), ankaraAirport.getCity(), "01/02/2024", "02/02/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertTrue(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByDepartureDateBetween_validDateRange_shouldReturnFlights()
    {
        var result = m_injection.getFlightService().findFlightsByDepartureDateBetween("01/02/2024", "20/02/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByDepartureDateBetween_withGivenInvalidDateRange_shouldReturnEmptyList()
    {

        var result = assertThrows(DataServiceException.class, () -> m_injection.getFlightService().findFlightsByDepartureDateBetween("01/23/2024", "20/02/2024", 1));
        assertNotNull(result);
        var msg = "Message: FlightService::findFlightsByDepartureDateBetween , Cause Message:Message: FlightService::parseDate , Cause Message:Text '01/23/2024' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 23";
        assertEquals(msg, result.getMessage());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDateBetween_validInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findFlightsByDepartureAirportAndDepartureDateBetween(istanbulAirport.getCity(), "01/02/2024", "20/02/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDateBetween_withGivenInvalidInputs_shouldReturnEmptyList()
    {
        var result = m_injection.getFlightService()
                .findFlightsByDepartureAirportAndDepartureDateBetween("ASD", "01/02/2024", "20/02/2024", 1);
        assertNotNull(result);
        assertTrue(((FlightsResponseDTO) result.data()).getFlights().isEmpty());
    }

    @Test
    public void findFlightsByPriceBetween_validPriceRange_shouldReturnFlights()
    {
        double minPrice = 100.0;
        double maxPrice = 500.0;
        var result = m_injection.getFlightService().findFlightsByPriceBetween(minPrice, maxPrice, 1);
        assertNotNull(result);
        assertEquals(3, ((FlightsResponseDTO) result.data()).getFlights().size());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDate_validInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findFlightsByDepartureAirportAndDepartureDate(istanbulAirport.getCity(), "01/01/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertEquals(1, data.getFlights().size());
        assertFalse(data.getFlights().isEmpty());
    }


    @Test
    public void findFlightsByArrivalAirportAndDepartureDate_validInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService().findFlightsByArrivalAirportAndDepartureDate(ankaraAirport.getCity()
                , "01/01/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
        assertEquals(1, data.getFlights().size());
    }

    @Test
    public void findFlightsByFromAndToAndFromDate_validInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findFlightsByFromAndToAndFromDate(istanbulAirport.getCity(), ankaraAirport.getCity(), "01/01/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
        assertEquals(1, data.getFlights().size());
    }

    @Test
    public void findCheapestFlightsWithinRange_validInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findCheapestFlightsWithinRange(istanbulAirport.getCity(), ankaraAirport.getCity(), "01/01/2024", "02/02/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());

        boolean isSortedAscending = range(0, data.getFlights().size() - 1)
                .allMatch(i -> data.getFlights().get(i).departure().price()
                        <= data.getFlights().get(i + 1).departure().price());
        assertTrue(isSortedAscending);

    }

    @Test
    public void findFlightsCityDateRange_validInputs_shouldReturnFlights()
    {
        var result = m_injection.getFlightService()
                .findFlightsCityDateRange(istanbulAirport.getCity(), "01/01/2024", "05/05/2024", 1);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @Test
    public void findByAirportAndDepartureDateAndPriceBetween_validInputs_shouldReturnFlights()
    { // compare by departure date
        var searchDTO = new SearchFullQualifiedComparePriceDTO(istanbulAirport.getCity(),
                ankaraAirport.getCity(), LocalDate.of(2024, 1, 1), Optional.of(LocalDate.of(2024, 4, 1)), 1, 100.0, 500.0);
        var result = m_injection.getFlightService().findByAirportAndDepartureDateAndPriceBetween(searchDTO);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }


    @Test
    public void findByAllAirportAndAllDateAndPriceBetween_validInputs_shouldReturnFlights()
    {
        var searchDTO = new SearchFullQualifiedComparePriceDTO(istanbulAirport.getCity(), ankaraAirport.getCity(),
                LocalDate.of(2024, 1, 1), Optional.of(LocalDate.of(2024, 4, 1)), 1, 100.0, 500.0);
        var result = m_injection.getFlightService().findByAllAirportAndAllDateAndPriceBetween(searchDTO);
        assertNotNull(result);
        var data = (FlightsResponseDTO) result.data();
        assertFalse(data.getFlights().isEmpty());
    }

    @AfterEach
    public void tearDown()
    {
        m_databaseCleaner.clearH2Database();
    }
}