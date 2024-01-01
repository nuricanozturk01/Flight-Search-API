package nuricanozturk.dev.service.search.flight.service.impl;

import callofproject.dev.library.exception.ISupplier;
import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.common.dto.FlightInfoDTO;
import nuricanozturk.dev.data.common.dto.FlightResponseDTO;
import nuricanozturk.dev.data.common.dto.FlightsResponseDTO;
import nuricanozturk.dev.data.common.dto.ResponseDTO;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedComparePriceDTO;
import nuricanozturk.dev.data.common.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.mapper.IFlightMapper;
import nuricanozturk.dev.service.search.flight.service.IFlightService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;
import static callofproject.dev.util.stream.StreamUtil.toList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static nuricanozturk.dev.service.search.flight.util.StringNormalization.convert;

/**
 * FlightService is a REST controller that provides endpoints for managing and retrieving flight information.
 * It includes functionalities such as finding flights by various criteria like airports, dates, prices, etc.
 */
@RestController
@RequestMapping("/api/flight")
public class FlightService implements IFlightService
{
    private final FlightServiceHelper m_flightServiceHelper;
    private final IFlightMapper m_flightMapper;

    /**
     * Constructs a FlightService with necessary dependencies.
     *
     * @param flightServiceHelper The service helper for flight operations.
     * @param flightMapper        The mapper to convert flight entities to DTOs.
     */
    public FlightService(FlightServiceHelper flightServiceHelper, IFlightMapper flightMapper)
    {
        m_flightServiceHelper = flightServiceHelper;
        m_flightMapper = flightMapper;
    }

    /**
     * Finds a flight by its ID.
     *
     * @param id The UUID of the flight.
     * @return An Optional of Flight if found.
     */
    @Override
    public Optional<Flight> findFlightById(UUID id)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightById(id), "FlightService::findFlightById");
    }

    /**
     * Finds flights arriving at a specified airport.
     *
     * @param arrivalAirport The airport code for the arrival airport.
     * @param page           The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByArrivalAirport(String arrivalAirport, int page)
    {
        ISupplier<Page<Flight>> flights = () -> m_flightServiceHelper.findFlightsByArrivalAirport(convert(arrivalAirport), page);

        return toFlightsResponseDTO(flights, "FlightService::findFlightsByArrivalAirport", page);
    }

    /**
     * Finds flights departing from a specified airport.
     *
     * @param departureAirport The airport code for the departure airport.
     * @param page             The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByDepartureAirport(String departureAirport, int page)
    {
        ISupplier<Page<Flight>> flights = () -> m_flightServiceHelper.findFlightsByDepartureAirport(convert(departureAirport), page);

        return toFlightsResponseDTO(flights, "FlightService::findFlightsByDepartureAirport", page);
    }

    /**
     * Finds flights by both arrival and departure airports.
     *
     * @param departureAirport The airport code for the departure airport.
     * @param arrivalAirport   The airport code for the arrival airport.
     * @param page             The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirport(String departureAirport, String arrivalAirport, int page)
    {
        ISupplier<Page<Flight>> flights = () -> m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirport(convert(arrivalAirport), convert(departureAirport), page);

        return toFlightsResponseDTO(flights, "FlightService::findFlightsByArrivalAirportAndDepartureAirport", page);
    }

    /**
     * Finds flights based on a fully qualified search criteria including locations and dates.
     *
     * @param dto The DTO containing search criteria.
     * @return A ResponseDTO containing the search results.
     */
    @Override
    public ResponseDTO findFlightsByFromAndToLocationAndDate(SearchFullQualifiedDTO dto)
    {
        checkSearchFullQualifiedDTO(dto);
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToLocationAndDate(convert(dto.arrivalAirport()), convert(dto.departureAirport()), dto.departureDate(), dto.returnDate().get(), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToLocationAndDate", dto.page());
    }


    /**
     * Finds flights between two locations on a specific date.
     *
     * @param from The departure location.
     * @param to   The arrival location.
     * @param date The date of travel.
     * @param page The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndDateBetween(convert(from), convert(to), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndDateBetween", page);
    }

    /**
     * Finds flights between two locations within a specified date range.
     *
     * @param from  The departure location.
     * @param to    The arrival location.
     * @param start The start date of the range.
     * @param end   The end date of the range.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndDateBetween(convert(from), convert(to), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndDateBetween", page);
    }

    /**
     * Finds flights departing between a specific date range.
     *
     * @param start The start date of the range.
     * @param end   The end date of the range.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByDepartureDateBetween(String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureDateBetween(parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureDateBetween", page);
    }

    /**
     * Finds flights departing from a specific airport between specified dates.
     *
     * @param from  The code of the departure airport.
     * @param start The start date in dd/MM/yyyy format.
     * @param end   The end date in dd/MM/yyyy format.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByDepartureAirportAndDepartureDateBetween(String from, String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDateBetween(convert(from), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureAirportAndDepartureDateBetween", page);
    }

    /**
     * Finds flights within a specified price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param page     The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByPriceBetween(double minPrice, double maxPrice, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByPriceBetween(minPrice, maxPrice, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByPriceBetween", page);
    }

    /**
     * Finds flights departing from a specific airport on a specific date.
     *
     * @param from The code of the departure airport.
     * @param date The departure date in dd/MM/yyyy format.
     * @param page The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByDepartureAirportAndDepartureDate(String from, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDate(convert(from), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureAirportAndDepartureDate", page);
    }

    /**
     * Finds flights arriving at a specific airport on a specific date.
     *
     * @param arrivalAirport The code of the arrival airport.
     * @param date           The date of arrival in dd/MM/yyyy format.
     * @param page           The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsByArrivalAirportAndDepartureDate(String arrivalAirport, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureDate(convert(arrivalAirport), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByArrivalAirportAndDepartureDate", page);
    }

    /**
     * Finds flights based on the provided departure and arrival locations, and a specific date.
     *
     * @param from The code of the departure airport.
     * @param to   The code of the arrival airport.
     * @param date The date of travel in dd/MM/yyyy format.
     * @param page The page number for pagination of results.
     * @return A ResponseDTO containing a list of flights that match the search criteria.
     */
    @Override
    public ResponseDTO findFlightsByFromAndToAndFromDate(String from, String to, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndFromDate(convert(from), convert(to), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndFromDate", page);
    }

    /**
     * Finds the cheapest flights within a specified range between two locations.
     *
     * @param from  The code of the departure airport.
     * @param to    The code of the arrival airport.
     * @param start The start date in dd/MM/yyyy format.
     * @param end   The end date in dd/MM/yyyy format.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findCheapestFlightsWithinRange(String from, String to, String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findCheapestFlightsWithinRange(convert(from), convert(to), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findCheapestFlightsWithinRange", page);
    }

    /**
     * Finds flights within a city and date range.
     *
     * @param city  The city code for either departure or arrival.
     * @param start The start date in dd/MM/yyyy format.
     * @param end   The end date in dd/MM/yyyy format.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    @Override
    public ResponseDTO findFlightsCityDateRange(String city, String start, String end, int page)
    {   //city is departure city or arrival city

        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsCityDateRange(convert(city), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsCityDateRange", page);
    }

    /**
     * Finds flights by airport, departure date, and within a price range.
     *
     * @param dto The DTO containing the search criteria.
     * @return A ResponseDTO containing the search results.
     */
    @Override
    public ResponseDTO findByAirportAndDepartureDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findAllByDepartureDateAndPriceRange(
                convert(dto.departureAirport()), convert(dto.arrivalAirport()),
                dto.departureDate(), dto.returnDate().get(),
                dto.minPrice(), dto.maxPrice(), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findByAirportAndDepartureDateAndPriceBetween", dto.page());
    }


    /**
     * Finds flights by all airports, all dates, and within a price range.
     *
     * @param dto The DTO containing the search criteria.
     * @return A ResponseDTO containing the search results.
     */
    @Override
    public ResponseDTO findByAllAirportAndAllDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto)
    {// All means contains tuples (departureAirport, arrivalAirport, departureDate, returnDate, minPrice, maxPrice...)

        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findByAllAirportAndAllDateAndPriceBetween(
                convert(dto.departureAirport()), convert(dto.arrivalAirport()),
                dto.departureDate(), dto.returnDate().get(),
                dto.minPrice(), dto.maxPrice(), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findByAllAirportAndAllDateAndPriceBetween", dto.page());
    }

    /**
     * Converts a page of Flight entities into a ResponseDTO containing FlightResponseDTOs.
     * This method is used to centralize the conversion of Flight entities to DTOs for various flight search operations.
     *
     * @param supplier The supplier that provides a Page of Flight entities.
     * @param msg      The message to log for this operation.
     * @param page     The current page number.
     * @return A ResponseDTO containing a list of FlightResponseDTOs and pagination details.
     */
    private ResponseDTO toFlightsResponseDTO(ISupplier<Page<Flight>> supplier, String msg, int page)
    {
        var flights = doForDataService(supplier, msg);

        var flightsInfo = m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO));

        var flightsResponseDTO = new FlightsResponseDTO(flightsInfo.flights().stream().map(this::generateFlightResponseDTO).toList());

        return new ResponseDTO(page, flights.getTotalPages(), flightsResponseDTO.getFlights().size(), "Success", flightsResponseDTO);
    }

    /**
     * Generates a FlightResponseDTO from a FlightInfoDTO.
     * It checks if there is a return flight information and includes it in the response if present.
     *
     * @param flightInfoDTO The FlightInfoDTO object containing flight information.
     * @return A FlightResponseDTO object containing flight details and optionally return flight details.
     */
    private FlightResponseDTO generateFlightResponseDTO(FlightInfoDTO flightInfoDTO)
    {
        if (flightInfoDTO.returnFlight() == null)
            return new FlightResponseDTO(flightInfoDTO, empty());

        return new FlightResponseDTO(flightInfoDTO, of(m_flightMapper.toFlightInfoDTO(flightInfoDTO.returnFlight())));
    }

    /**
     * Parses a date string in the format dd/MM/yyyy to a LocalDate object.
     *
     * @param date The date string to parse.
     * @return The LocalDate representation of the given date string.
     */
    private LocalDate parseDate(String date)
    {
        return doForDataService(() -> LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), "FlightService::parseDate");
    }

    /**
     * Validates the SearchFullQualifiedDTO object for correct data.
     *
     * @param dto The DTO to be validated.
     */
    private void checkSearchFullQualifiedDTO(SearchFullQualifiedDTO dto)
    {
        if (dto.returnDate().isEmpty())
            throw new DataServiceException("Return date cannot be empty");
        if (dto.departureDate().isAfter(dto.returnDate().get()))
            throw new DataServiceException("Departure date cannot be after return date");
        if (dto.departureDate().isAfter(dto.returnDate().get()))
            throw new DataServiceException("Departure date cannot be after return date");
    }
}