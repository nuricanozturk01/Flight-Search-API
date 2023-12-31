package nuricanozturk.dev.service.search.flight.controller;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.common.dto.ResponseDTO;
import nuricanozturk.dev.data.common.dto.request.CreateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.CreateFlightDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.service.search.flight.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static nuricanozturk.dev.service.search.flight.DataProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest
{
    @Mock
    private IAdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @Test
    public void createFlightTest_withGivenValidInput_shouldReturnOk()
    {
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(adminService.createFlight(any(CreateFlightDTO.class))).thenReturn(responseDTO);

        ResponseEntity<Object> response = adminController.createFlight(provideCreateFlightDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    public void createFlightTest_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(adminService.createFlight(any(CreateFlightDTO.class))).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> responseFail = adminController.createFlight(provideCreateFlightDTO());

        assertEquals(HttpStatus.BAD_REQUEST, responseFail.getStatusCode());
    }

    @Test
    public void createAirportTest_withGivenValidInput_shouldReturnOk()
    {
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideAnkaraAirport());

        when(adminService.createAirport(any(CreateAirportDTO.class))).thenReturn(responseDTO);

        ResponseEntity<Object> response = adminController.createAirport(provideCreateAirportDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    public void createAirportTest_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(adminService.createAirport(any(CreateAirportDTO.class))).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> response = adminController.createAirport(provideCreateAirportDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateFlightTest_withGivenValidInput_shouldReturnOk()
    {
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport()));

        when(adminService.updateFlight(any(UpdateFlightDTO.class))).thenReturn(responseDTO);

        ResponseEntity<Object> response = adminController.updateFlight(provideUpdateFlightDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateFlightTest_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(adminService.updateFlight(any(UpdateFlightDTO.class))).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> response = adminController.updateFlight(provideUpdateFlightDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateAirportTest_withGivenValidInput_shouldReturnOk()
    {
        var responseDTO = new ResponseDTO(null, null, null, "Success", provideAnkaraAirport());

        when(adminService.updateAirport(any(UpdateAirportDTO.class))).thenReturn(responseDTO);

        ResponseEntity<Object> response = adminController.updateAirport(provideUpdateAirportDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateAirportTest_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(adminService.updateAirport(any(UpdateAirportDTO.class))).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> response = adminController.updateAirport(provideUpdateAirportDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteFlightTest_withGivenValidInput_shouldReturnOk()
    {
        var responseDTO = new ResponseDTO(null, null, null, "Success", null);

        when(adminService.deleteFlightById(any())).thenReturn(responseDTO);

        var oneWayFlight = provideOneWayFlight(provideAnkaraAirport(), provideIstanbulAirport());

        ResponseEntity<Object> response = adminController.deleteFlightById(oneWayFlight.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteFlightTest_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(adminService.deleteFlightById(any())).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> response = adminController.deleteFlightById(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteAirportTest_withGivenValidInput_shouldReturnOk()
    {
        var responseDTO = new ResponseDTO(null, null, null, "Success", null);

        when(adminService.deleteAirportByCityName(any(String.class))).thenReturn(responseDTO);

        var ankaraAirport = provideAnkaraAirport();

        ResponseEntity<Object> response = adminController.deleteAirport(ankaraAirport.getCity());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteAirportTest_withGivenInvalidInput_shouldReturnBadRequest()
    {
        when(adminService.deleteAirportByCityName(any(String.class))).thenThrow(new DataServiceException("Fail"));

        ResponseEntity<Object> response = adminController.deleteAirport(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
