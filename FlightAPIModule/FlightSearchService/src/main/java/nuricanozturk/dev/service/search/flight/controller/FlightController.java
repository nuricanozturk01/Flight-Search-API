package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.service.search.flight.dto.ErrorMessage;
import nuricanozturk.dev.service.search.flight.dto.request.SearchFullQualifiedComparePriceDTO;
import nuricanozturk.dev.service.search.flight.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.service.search.flight.service.impl.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> findFlightsWithFullQualified(@RequestBody SearchFullQualifiedDTO searchFullQualifiedDTO)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween(searchFullQualifiedDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/airport")
    public ResponseEntity<Object> findFlightsByLocations(@RequestParam("from") String fromCity,
                                                         @RequestParam("to") String toCity,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureAirport(fromCity, toCity, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("deneme")
    public ResponseEntity<Object> findByDepartureAirportCityAndArrivalAirportCityAndDepartureDateBetweenAndReturnDateBetweenAndPriceBetween
            (@RequestBody SearchFullQualifiedComparePriceDTO dto)
    {
        return subscribe(() -> ok(m_flightService.findByAirportAndDepartureDateAndPriceBetween(dto)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));

    }


}
