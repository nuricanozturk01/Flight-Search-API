package nuricanozturk.dev.service.search.flight.controller;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.common.dto.ResponseDTO;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedComparePriceDTO;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.service.search.flight.service.impl.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static java.util.Optional.of;
import static nuricanozturk.dev.service.search.flight.DataProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest
{
    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @Test
    public void findFlightById_existingId_shouldReturnFlight()
    {
        //given
        var responseDTO = of(provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));
        when(flightService.findFlightById(any(UUID.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightById(UUID.randomUUID());
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightById_nonExistingId_shouldReturnNotFound()
    {
        //given
        when(flightService.findFlightById(any(UUID.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> response = flightController.findFlightById(UUID.randomUUID());
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void findFlightsByArrivalAirport_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByArrivalAirportAndDepartureAirport(any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByLocations("ANK", "IST", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByArrivalAirport_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByArrivalAirportAndDepartureAirport(any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByLocations("ANK", "IST", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureAirport_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByDepartureAirport(any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByDepartureAirport("ANK", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureAirport_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(flightService.findFlightsByDepartureAirport(any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> responseFail = flightController.findFlightsByDepartureAirport("ANK", 1);

        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDateBetween_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByDepartureAirportAndDepartureDateBetween(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByDepartureAirportAndDepartureDateBetween("ANK", "2024-01-01", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDateBetween_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByDepartureAirportAndDepartureDateBetween(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByDepartureAirportAndDepartureDateBetween("ANK", "2024-01-01", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDate_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByDepartureAirportAndDepartureDate(any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByDepartureAirportAndDepartureDate("ANK", "IST", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureAirportAndDepartureDate_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByDepartureAirportAndDepartureDate(any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByDepartureAirportAndDepartureDate("ANK", "IST", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByFromAndToLocationAndDate_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByFromAndToLocationAndDate(any(SearchFullQualifiedDTO.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsWithFullQualified(provideSearchFullQualifiedDTO());
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByFromAndToLocationAndDate_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByFromAndToLocationAndDate(any(SearchFullQualifiedDTO.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsWithFullQualified(provideSearchFullQualifiedDTO());
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByFromAndToAndDateBetween_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByFromAndToAndDateBetween(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByFromAndToAndDateBetween("ANK", "IST", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByFromAndToAndDateBetween_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByFromAndToAndDateBetween(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByFromAndToAndDateBetween("ANK", "IST", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findByAirportAndDepartureDateAndPriceBetween_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findByAirportAndDepartureDateAndPriceBetween(any(SearchFullQualifiedComparePriceDTO.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findByAirportAndDepartureDateAndPriceBetween(provideSearchFullQualifiedComparePriceDTO());
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findByAirportAndDepartureDateAndPriceBetween_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findByAirportAndDepartureDateAndPriceBetween(any(SearchFullQualifiedComparePriceDTO.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findByAirportAndDepartureDateAndPriceBetween(provideSearchFullQualifiedComparePriceDTO());
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findByAllAirportAndAllDateAndPriceBetween_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findByAllAirportAndAllDateAndPriceBetween(any(SearchFullQualifiedComparePriceDTO.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findByAllAirportAndAllDateAndPriceBetween(provideSearchFullQualifiedComparePriceDTO());
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findByAllAirportAndAllDateAndPriceBetween_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findByAllAirportAndAllDateAndPriceBetween(any(SearchFullQualifiedComparePriceDTO.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findByAllAirportAndAllDateAndPriceBetween(provideSearchFullQualifiedComparePriceDTO());
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureDateBetween_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByDepartureDateBetween(any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByDepartureDateBetween("2024-01-01", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByDepartureDateBetween_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByDepartureDateBetween(any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByDepartureDateBetween("2024-01-01", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }


    @Test
    public void findFlightsByPriceBetween_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideRoundTrapFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByPriceBetween(any(Double.class), any(Double.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByPriceBetween(100, 1000, 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByPriceBetween_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByPriceBetween(any(Double.class), any(Double.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByPriceBetween(100, 1000, 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByArrivalAirportAndDepartureDate_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideRoundTrapFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByArrivalAirportAndDepartureDate(any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByArrivalAirportAndDepartureDate("ANK", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByArrivalAirportAndDepartureDate_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByArrivalAirportAndDepartureDate(any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByArrivalAirportAndDepartureDate("ANK", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsByFromAndToAndFromDate_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideRoundTrapFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsByFromAndToAndFromDate(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsByFromAndToAndFromDate("ANK", "IST", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsByFromAndToAndFromDate_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsByFromAndToAndFromDate(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsByFromAndToAndFromDate("ANK", "IST", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findCheapestFlightsWithinRange_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideRoundTrapFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findCheapestFlightsWithinRange(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findCheapestFlightsWithinRange("ANK", "IST", "2024-01-01", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findCheapestFlightsWithinRange_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findCheapestFlightsWithinRange(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findCheapestFlightsWithinRange("ANK", "IST", "2024-01-01", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void findFlightsCityDateRange_withGivenValidInput_shouldReturnOk()
    {
        //given
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideRoundTrapFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(flightService.findFlightsCityDateRange(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(responseDTO);
        //Act
        ResponseEntity<Object> response = flightController.findFlightsCityDateRange("ANK", "IST", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFlightsCityDateRange_withGivenInvalidInput_shouldReturnBadRequest()
    {
        //given
        when(flightService.findFlightsCityDateRange(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenThrow(new DataServiceException("Fail"));
        //Act
        ResponseEntity<Object> responseFail = flightController.findFlightsCityDateRange("ANK", "IST", "2024-01-01", 1);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }
}
