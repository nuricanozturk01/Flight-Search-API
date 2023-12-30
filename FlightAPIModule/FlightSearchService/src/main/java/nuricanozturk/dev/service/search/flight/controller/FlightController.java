package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.data.common.dto.ErrorMessage;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedComparePriceDTO;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.service.search.flight.service.impl.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

import static callofproject.dev.library.exception.util.ExceptionUtil.subscribe;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/flight")
@SecurityRequirement(name = "Authorization")
public class FlightController
{

    private final FlightService m_flightService;

    public FlightController(FlightService flightService)
    {
        m_flightService = flightService;
    }

    @GetMapping("/search/full-qualified")
    public ResponseEntity<Object> findFlightsWithFullQualified(@RequestBody SearchFullQualifiedDTO searchFullQualifiedDTO)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByFromAndToLocationAndDate(searchFullQualifiedDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/by-airports")
    public ResponseEntity<Object> findFlightsByLocations(@RequestParam("from") String fromCity,
                                                         @RequestParam("to") String toCity,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureAirport(fromCity, toCity, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/airport-date-price-range")
    public ResponseEntity<Object> findByAirportAndDepartureDateAndPriceBetween(@RequestBody SearchFullQualifiedComparePriceDTO dto)
    {
        return subscribe(() -> ok(m_flightService.findByAirportAndDepartureDateAndPriceBetween(dto)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));

    }

    @GetMapping("/search/id")
    public ResponseEntity<Object> findFlightById(@RequestParam UUID id)
    {
        return subscribe(() -> ok(m_flightService.findFlightById(id)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/arrival-airport")
    public ResponseEntity<Object> findFlightsByArrivalAirport(@RequestParam("arrival") String arrivalAirport, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirport(arrivalAirport, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }


    @GetMapping("/search/departure-airport")
    public ResponseEntity<Object> findFlightsByDepartureAirport(@RequestParam String from, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureAirport(from, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/flights/airport-date-price-range")
    public ResponseEntity<Object> findByAllAirportAndAllDateAndPriceBetween(@RequestBody SearchFullQualifiedComparePriceDTO dto)
    {
        return subscribe(() -> ok(m_flightService.findByAllAirportAndAllDateAndPriceBetween(dto)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/by-origin-destination-date")
    public ResponseEntity<Object> findFlightsByFromAndToAndDateBetween(@RequestParam String from, @RequestParam String to, @RequestParam LocalDate date,
                                                                       @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByFromAndToAndDateBetween(from, to, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/by-departure-date-range")
    public ResponseEntity<Object> findFlightsByDepartureDateBetween(@RequestParam LocalDate start, @RequestParam LocalDate end,
                                                                    @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureDateBetween(start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/departure-airport-date-range")
    public ResponseEntity<Object> findFlightsByDepartureAirportAndDepartureDateBetween(@RequestParam String from, @RequestParam LocalDate start,
                                                                                       @RequestParam LocalDate end, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureAirportAndDepartureDateBetween(from, start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/price-range")
    public ResponseEntity<Object> findFlightsByPriceBetween(@RequestParam("min") double minPrice, @RequestParam("max") double maxPrice,
                                                            @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByPriceBetween(minPrice, maxPrice, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/departure-airport-specific-date")
    public ResponseEntity<Object> findFlightsByDepartureAirportAndDepartureDate(@RequestParam String from, @RequestParam LocalDate date,
                                                                                @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureAirportAndDepartureDate(from, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/arrival-airport-specific-date")
    public ResponseEntity<Object> findFlightsByArrivalAirportAndDepartureDate(@RequestParam("arrival") String arrivalAirport, @RequestParam LocalDate date,
                                                                              @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureDate(arrivalAirport, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/from-to-specific-date")
    public ResponseEntity<Object> findFlightsByFromAndToAndFromDate(@RequestParam String from, @RequestParam String to, @RequestParam LocalDate date,
                                                                    @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByFromAndToAndFromDate(from, to, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/cheapest-from-to-date-range")
    public ResponseEntity<Object> findCheapestFlightsWithinRange(@RequestParam String from, @RequestParam String to, @RequestParam LocalDate start,
                                                                 @RequestParam LocalDate end, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findCheapestFlightsWithinRange(from, to, start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("/search/city-date-range")
    public ResponseEntity<Object> findFlightsCityDateRange(@RequestParam String city, @RequestParam LocalDate start, @RequestParam LocalDate end,
                                                           @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsCityDateRange(city, start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }
}
