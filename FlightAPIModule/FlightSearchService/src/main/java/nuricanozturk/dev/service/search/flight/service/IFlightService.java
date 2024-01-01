package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.data.common.dto.ResponseDTO;
import nuricanozturk.dev.data.common.dto.request.*;
import nuricanozturk.dev.data.flight.entity.Flight;

import java.util.Optional;
import java.util.UUID;

/**
 * IFlightService interface is a REST controller that provides endpoints for managing and retrieving flight information.
 * It includes functionalities such as finding flights by various criteria like airports, dates, prices, etc.
 */
public interface IFlightService
{
    /**
     * Finds a flight by its ID.
     *
     * @param id The UUID of the flight.
     * @return An Optional of Flight if found.
     */
    Optional<Flight> findFlightById(UUID id);

    /**
     * Finds flights arriving at a specified airport.
     *
     * @param arrivalAirport The airport code for the arrival airport.
     * @param page           The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByArrivalAirport(String arrivalAirport, int page);

    /**
     * Finds flights departing from a specified airport.
     *
     * @param departureAirport The airport code for the departure airport.
     * @param page             The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByDepartureAirport(String departureAirport, int page);

    /**
     * Finds flights by both arrival and departure airports.
     *
     * @param departureAirport The airport code for the departure airport.
     * @param arrivalAirport   The airport code for the arrival airport.
     * @param page             The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByArrivalAirportAndDepartureAirport(String departureAirport, String arrivalAirport, int page);

    /**
     * Finds flights based on a fully qualified search criteria including locations and dates.
     *
     * @param dto The DTO containing search criteria.
     * @return A ResponseDTO containing the search results.
     */
    ResponseDTO findFlightsByFromAndToLocationAndDate(SearchFullQualifiedDTO dto);

    /**
     * Finds flights between two locations on a specific date.
     *
     * @param from The departure location.
     * @param to   The arrival location.
     * @param date The date of travel.
     * @param page The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, String date, int page);

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
    ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, String start, String end, int page);

    /**
     * Finds flights departing between a specific date range.
     *
     * @param start The start date of the range.
     * @param end   The end date of the range.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByDepartureDateBetween(String start, String end, int page);

    /**
     * Finds flights departing from a specific airport between specified dates.
     *
     * @param from  The code of the departure airport.
     * @param start The start date in dd/MM/yyyy format.
     * @param end   The end date in dd/MM/yyyy format.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByDepartureAirportAndDepartureDateBetween(String from, String start, String end, int page);

    /**
     * Finds flights within a specified price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param page     The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByPriceBetween(double minPrice, double maxPrice, int page);

    /**
     * Finds flights departing from a specific airport on a specific date.
     *
     * @param from The code of the departure airport.
     * @param date The departure date in dd/MM/yyyy format.
     * @param page The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByDepartureAirportAndDepartureDate(String from, String date, int page);

    /**
     * Finds flights arriving at a specific airport on a specific date.
     *
     * @param arrivalAirport The code of the arrival airport.
     * @param date           The date of arrival in dd/MM/yyyy format.
     * @param page           The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsByArrivalAirportAndDepartureDate(String arrivalAirport, String date, int page);

    /**
     * Finds flights based on the provided departure and arrival locations, and a specific date.
     *
     * @param from The code of the departure airport.
     * @param to   The code of the arrival airport.
     * @param date The date of travel in dd/MM/yyyy format.
     * @param page The page number for pagination of results.
     * @return A ResponseDTO containing a list of flights that match the search criteria.
     */
    ResponseDTO findFlightsByFromAndToAndFromDate(String from, String to, String date, int page);

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
    ResponseDTO findCheapestFlightsWithinRange(String from, String to, String start, String end, int page);

    /**
     * Finds flights within a city and date range.
     *
     * @param city  The city code for either departure or arrival.
     * @param start The start date in dd/MM/yyyy format.
     * @param end   The end date in dd/MM/yyyy format.
     * @param page  The page number for pagination.
     * @return A ResponseDTO containing a list of flights.
     */
    ResponseDTO findFlightsCityDateRange(String city, String start, String end, int page);

    /**
     * Finds flights by airport, departure date, and within a price range.
     *
     * @param dto The DTO containing the search criteria.
     * @return A ResponseDTO containing the search results.
     */
    ResponseDTO findByAirportAndDepartureDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto);

    /**
     * Finds flights by all airports, all dates, and within a price range.
     *
     * @param dto The DTO containing the search criteria.
     * @return A ResponseDTO containing the search results.
     */
    ResponseDTO findByAllAirportAndAllDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto);
}
