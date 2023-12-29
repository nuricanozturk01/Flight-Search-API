package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.service.search.flight.dto.ErrorMessage;
import nuricanozturk.dev.service.search.flight.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.service.search.flight.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static callofproject.dev.library.exception.util.ExceptionUtil.subscribe;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/search/flight")
@SecurityRequirement(name = "Authorization")
public class FlightController
{
    private final FlightService m_flightService;

    public FlightController(FlightService flightService)
    {
        m_flightService = flightService;
    }

    @GetMapping("/full")
    public ResponseEntity<Object> findFlightsWithFullQualifiers(@RequestBody SearchFullQualifiedDTO searchFullQualifiedDTO)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween(searchFullQualifiedDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

}
