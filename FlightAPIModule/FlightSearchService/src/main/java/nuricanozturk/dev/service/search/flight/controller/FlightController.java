package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.data.common.dto.ErrorMessage;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedComparePriceDTO;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.service.search.flight.service.IFlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static callofproject.dev.library.exception.util.ExceptionUtil.subscribe;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * FlightController is a REST controller responsible for handling flight-related API requests.
 * It provides endpoints for searching and retrieving flight information based on various criteria.
 */
@RestController
@RequestMapping("/api/flight")
@SecurityRequirement(name = "Authorization")
public class FlightController
{
    private final IFlightService m_flightService;

    /**
     * Constructs a FlightController with the specified flight service.
     *
     * @param flightService The service to handle flight operations.
     */
    public FlightController(IFlightService flightService)
    {
        m_flightService = flightService;
    }

    /**
     * Finds flights based on fully qualified search criteria including locations and dates.
     *
     * @param searchFullQualifiedDTO The DTO containing search criteria.
     * @return ResponseEntity with the search results or error message.
     */
    @PostMapping("/search/full-qualified")
    @Operation(summary = "Finds flights based on fully qualified search criteria including locations and dates",
            description = """
                    This query is used to find flights based on specific departure and arrival airport cities, a designated departure date, and a designated return date.
                    Must enter the departure and arrival airport codes, departure and return dates, and the number of passengers.
                    Dates must be entered in the format dd/MM/yyyy(25/01/1999).
                    """)
    public ResponseEntity<Object> findFlightsWithFullQualified(@RequestBody(required = false) SearchFullQualifiedDTO searchFullQualifiedDTO)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByFromAndToLocationAndDate(searchFullQualifiedDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights between specified departure and arrival locations.
     *
     * @param fromCity The code of the departure city.
     * @param toCity   The code of the arrival city.
     * @param page     The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/by-airports")
    @Operation(summary = "Finds flights between specified departure and arrival locations",
            description = """
                    This query is used to find flights based on specific departure and arrival airport cities.
                    """)
    public ResponseEntity<Object> findFlightsByLocations(@RequestParam("from") String fromCity,
                                                         @RequestParam("to") String toCity,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureAirport(fromCity, toCity, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights based on airport codes, departure date, and a price range.
     *
     * @param dto The DTO containing the search criteria.
     * @return ResponseEntity with the search results or error message.
     */
    @PostMapping("/search/airport-date-price-range")
    @Operation(summary = "Finds flights based on airport codes, departure date, and a price range",
            description = """
                    This query is used to find flights based on specific departure and arrival airport cities, a designated date range, and a specified price range.
                    Must enter the departure and arrival airport codes, departure and return dates, and the number of passengers.
                    Dates must be entered in the format dd/MM/yyyy(25/01/1999).
                     """)
    public ResponseEntity<Object> findByAirportAndDepartureDateAndPriceBetween(@RequestBody SearchFullQualifiedComparePriceDTO dto)
    {
        return subscribe(() -> ok(m_flightService.findByAirportAndDepartureDateAndPriceBetween(dto)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));

    }

    /**
     * Finds a flight by its unique identifier.
     *
     * @param id The UUID of the flight.
     * @return ResponseEntity with the flight details or error message.
     */
    @GetMapping("/search/id")
    @Operation(summary = "Finds a flight by its unique identifier",
            description = """
                    This query is used to find a flight by its unique identifier.
                    """)
    public ResponseEntity<Object> findFlightById(@RequestParam UUID id)
    {
        return subscribe(() -> ok(m_flightService.findFlightById(id)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights arriving at a specified airport.
     *
     * @param arrivalAirport The code of the arrival airport.
     * @param page           The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/arrival-airport")
    @Operation(summary = "Finds flights arriving at a specified airport",
            description = """
                    This query lists the airplanes that land at the specified airport.
                    """)
    public ResponseEntity<Object> findFlightsByArrivalAirport(@RequestParam("arrival") String arrivalAirport, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirport(arrivalAirport, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights departing from a specified airport.
     *
     * @param from The code of the departure airport.
     * @param page The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/departure-airport")
    @Operation(summary = "Finds flights departing from a specified airport",
            description = """
                    This query lists the airplanes that depart from the specified airport.
                    """)
    public ResponseEntity<Object> findFlightsByDepartureAirport(@RequestParam String from, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureAirport(from, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights based on all airports, dates, and within a price range.
     *
     * @param dto The DTO containing the search criteria.
     * @return ResponseEntity with the search results or error message.
     */
    @PostMapping("/flights/airport-date-price-range")
    @Operation(summary = "Finds flights based on all airports, dates, and within a price range",
            description = """
                    This query is used to find flights based on all airports, dates, and within a price range.
                    Must enter the departure and arrival airport codes, departure and return dates, and the number of passengers.
                    Dates must be entered in the format dd/MM/yyyy(25/01/1999).
                    """)
    public ResponseEntity<Object> findByAllAirportAndAllDateAndPriceBetween(@RequestBody SearchFullQualifiedComparePriceDTO dto)
    {
        return subscribe(() -> ok(m_flightService.findByAllAirportAndAllDateAndPriceBetween(dto)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights between two locations on a specific date.
     *
     * @param from The departure location.
     * @param to   The arrival location.
     * @param date The date for the flights.
     * @param page The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/by-origin-destination-date")
    @Operation(summary = "Finds flights between two locations on a specific date",
            description = """
                    This query is used to find flights based on a specific departure and arrival airport city along with a designated departure date.
                    """)
    public ResponseEntity<Object> findFlightsByFromAndToAndDateBetween(@RequestParam String from, @RequestParam String to, @RequestParam String date,
                                                                       @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByFromAndToAndDateBetween(from, to, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights departing between a specified date range.
     *
     * @param start The start date.
     * @param end   The end date.
     * @param page  The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/by-departure-date-range")
    @Operation(summary = "Finds flights departing between a specified date range",
            description = """
                    This query lists flights that depart within a specified date range.
                    """)
    public ResponseEntity<Object> findFlightsByDepartureDateBetween(@RequestParam String start, @RequestParam String end,
                                                                    @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureDateBetween(start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights departing from a specific airport within a date range.
     *
     * @param from  The code of the departure airport.
     * @param start The start date.
     * @param end   The end date.
     * @param page  The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/departure-airport-date-range")
    @Operation(summary = "Finds flights departing from a specific airport within a date range",
            description = """
                    This query is used to find flights based on a specific departure airport city and a designated date range.
                    """)
    public ResponseEntity<Object> findFlightsByDepartureAirportAndDepartureDateBetween(@RequestParam String from, @RequestParam String start,
                                                                                       @RequestParam String end, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureAirportAndDepartureDateBetween(from, start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights within a specific price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param page     The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/price-range")
    @Operation(summary = "Finds flights within a specific price range",
            description = """
                    This query lists flights within a specified price range.
                    """)
    public ResponseEntity<Object> findFlightsByPriceBetween(@RequestParam("min") double minPrice, @RequestParam("max") double maxPrice,
                                                            @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByPriceBetween(minPrice, maxPrice, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights departing from a specific airport on a specific date.
     *
     * @param from The code of the departure airport.
     * @param date The specific date.
     * @param page The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/departure-airport-specific-date")
    @Operation(summary = "Finds flights departing from a specific airport on a specific date",
            description = """
                    This query lists the airplanes that depart from the specified airport on the designated date.
                    """)
    public ResponseEntity<Object> findFlightsByDepartureAirportAndDepartureDate(@RequestParam String from, @RequestParam String date,
                                                                                @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByDepartureAirportAndDepartureDate(from, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights arriving at a specific airport on a specific date.
     *
     * @param arrivalAirport The code of the arrival airport.
     * @param date           The specific date.
     * @param page           The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/arrival-airport-specific-date")
    @Operation(summary = "Finds flights arriving at a specific airport on a specific date",
            description = """
                    This query lists the airplanes that land at the specified airport on the designated date.
                    """)
    public ResponseEntity<Object> findFlightsByArrivalAirportAndDepartureDate(@RequestParam("arrival") String arrivalAirport, @RequestParam String date,
                                                                              @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByArrivalAirportAndDepartureDate(arrivalAirport, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights between two locations on a specific date.
     *
     * @param from The departure location.
     * @param to   The arrival location.
     * @param date The specific date.
     * @param page The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/from-to-specific-date")
    @Operation(summary = "Finds flights between two locations on a specific date",
            description = """
                    This query finds flights based on specific departure and arrival cities and a designated date.
                    """)
    public ResponseEntity<Object> findFlightsByFromAndToAndFromDate(@RequestParam String from, @RequestParam String to, @RequestParam String date,
                                                                    @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsByFromAndToAndFromDate(from, to, date, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds the cheapest flights within a date range between two locations.
     *
     * @param from  The departure location.
     * @param to    The arrival location.
     * @param start The start date.
     * @param end   The end date.
     * @param page  The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */
    @GetMapping("/search/cheapest-from-to-date-range")
    @Operation(summary = "Finds the cheapest flights within a date range between two locations",
            description = """
                    This query lists the cheapest flights ascending order between two locations within a specified date range.
                    """)
    public ResponseEntity<Object> findCheapestFlightsWithinRange(@RequestParam String from, @RequestParam String to, @RequestParam String start,
                                                                 @RequestParam String end, @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findCheapestFlightsWithinRange(from, to, start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Finds flights within a city and date range.
     *
     * @param city  The city code for either departure or arrival.
     * @param start The start date.
     * @param end   The end date.
     * @param page  The page number for pagination.
     * @return ResponseEntity with the search results or error message.
     */

    @GetMapping("/search/city-date-range")
    @Operation(summary = "Finds flights within a city and date range",
            description = """
                    This query lists flights that either depart from or arrive at a specific city and occur 
                    within a specified date range.
                    """)
    public ResponseEntity<Object> findFlightsCityDateRange(@RequestParam String city, @RequestParam String start, @RequestParam String end,
                                                           @RequestParam(value = "p", defaultValue = "1") int page)
    {
        return subscribe(() -> ok(m_flightService.findFlightsCityDateRange(city, start, end, page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }
}
