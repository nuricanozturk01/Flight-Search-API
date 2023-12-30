package nuricanozturk.dev.data.flight.repository;

import nuricanozturk.dev.data.flight.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

/**
 * IFlightRepository is a JPA repository for handling operations related to the Flight entity.
 * It provides methods to perform various queries on the Flight data in the database.
 */
@Repository
public interface IFlightRepository extends JpaRepository<Flight, UUID>
{
    /**
     * Finds flights by the arrival airport city.
     *
     * @param p_arrivalAirport The city of the arrival airport.
     * @param pageable         The pagination information.
     * @return A page of flights arriving at the specified airport city.
     */
    @Query("from Flight where arrivalAirport.city = :p_arrivalAirport")
    Page<Flight> findFlightsByArrivalAirport(@Param("p_arrivalAirport") String p_arrivalAirport, Pageable pageable);

    /**
     * Finds flights by the departure airport city.
     *
     * @param p_departureAirport The city of the departure airport.
     * @param pageable           The pagination information.
     * @return A page of flights departing from the specified airport city.
     */
    @Query("from Flight where departureAirport.city = :p_departureAirport")
    Page<Flight> findFlightsByDepartureAirport(@Param("p_departureAirport") String p_departureAirport, Pageable pageable);

    /**
     * Finds flights by both arrival and departure airport cities.
     *
     * @param p_arrivalAirport   The city of the arrival airport.
     * @param p_departureAirport The city of the departure airport.
     * @param pageable           The pagination information.
     * @return A page of flights matching the specified arrival and departure cities.
     */
    @Query("from Flight where arrivalAirport.city = :p_arrivalAirport and departureAirport.city = :p_departureAirport")
    Page<Flight> findFlightsByArrivalAirportAndDepartureAirport(@Param("p_arrivalAirport") String p_arrivalAirport,
                                                                @Param("p_departureAirport") String p_departureAirport,
                                                                Pageable pageable);

    /**
     * Finds flights by arrival and departure airport cities, and between departure and return dates.
     *
     * @param p_arrivalAirport   The city of the arrival airport.
     * @param p_departureAirport The city of the departure airport.
     * @param p_departureDate    The departure date of the flights.
     * @param p_returnDate       The return date of the flights.
     * @param pageable           The pagination information.
     * @return A page of flights matching the specified criteria.
     */
    @Query("""
            from Flight where
            arrivalAirport.city = :p_arrivalAirport
            and departureAirport.city = :p_departureAirport
            and departureDate = :p_departureDate
            and returnDate = :p_returnDate
            """)
    Page<Flight> findFlightsByFromAndToLocationAndDate(@Param("p_arrivalAirport") String p_arrivalAirport,
                                                       @Param("p_departureAirport") String p_departureAirport,
                                                       @Param("p_departureDate") LocalDate p_departureDate,
                                                       @Param("p_returnDate") LocalDate p_returnDate,
                                                       Pageable pageable);

    /**
     * Finds flights by arrival and departure airport cities and a specific departure date.
     *
     * @param p_arrivalAirport   The city of the arrival airport.
     * @param p_departureAirport The city of the departure airport.
     * @param p_departureDate    The specific departure date for the flights.
     * @param pageable           The pagination information.
     * @return A page of flights matching the specified arrival and departure airports and departure date.
     */

    @Query("""
            from Flight where arrivalAirport.city = :p_arrivalAirport and departureAirport.city = :p_departureAirport
            and departureDate = :p_departureDate\s
            """)
    Page<Flight> findFlightsByFromAndToAndDateBetween(@Param("p_arrivalAirport") String p_arrivalAirport,
                                                      @Param("p_departureAirport") String p_departureAirport,
                                                      @Param("p_departureDate") LocalDate p_departureDate,
                                                      Pageable pageable);

    /**
     * Finds flights by arrival airport and departure airport within a specified date range.
     *
     * @param departureAirport The departure airport entity.
     * @param arrivalAirport   The arrival airport entity.
     * @param startDate        The start date of the search range.
     * @param endDate          The end date of the search range.
     * @param pageable         The pagination information.
     * @return A page of flights matching the specified criteria.
     */
    @Query("""
            from Flight where departureAirport.city = :departureAirport and arrivalAirport.city = :arrivalAirport
            and departureDate between :startDate and :endDate
            """)
    Page<Flight> findFlightsByDepartureAirportAndArrivalAirportAndDepartureDateBetween(String departureAirport,
                                                                                       String arrivalAirport,
                                                                                       LocalDate startDate,
                                                                                       LocalDate endDate,
                                                                                       Pageable pageable);


    /**
     * Finds flights within a specified departure date range.
     *
     * @param startDate The start date of the search range.
     * @param endDate   The end date of the search range.
     * @param pageable  The pagination information.
     * @return A page of flights departing within the specified date range.
     */
    Page<Flight> findFlightsByDepartureDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Finds flights departing from a specific airport within a specified date range.
     *
     * @param departureAirport The departure airport entity.
     * @param departureDate    The start date of the search range.
     * @param returnDate       The end date of the search range.
     * @param pageable         The pagination information.
     * @return A page of flights matching the specified criteria.
     */
    @Query("""
            from Flight as f where f.departureAirport.city = :departureAirport
            and f.departureDate between :departureDate and :returnDate
            """)
    Page<Flight> findFlightsByDepartureAirportAndDepartureDateBetween(@Param("departureAirport") String departureAirport,
                                                                      @Param("departureDate") LocalDate departureDate,
                                                                      @Param("returnDate") LocalDate returnDate,
                                                                      Pageable pageable);

    /**
     * Finds flights within a specific price range.
     *
     * @param minPrice The minimum price of the flights.
     * @param maxPrice The maximum price of the flights.
     * @param pageable The pagination information.
     * @return A page of flights within the specified price range.
     */
    Page<Flight> findFlightsByPriceBetween(double minPrice, double maxPrice, Pageable pageable);

    /**
     * Finds flights departing from a specific airport on a specific date.
     *
     * @param departureAirport The departure airport entity.
     * @param departureDate    The specific date of departure.
     * @return A page of flights departing from the specified airport on the specified date.
     */
    @Query("""
             from Flight as f where f.departureAirport.city = :departureAirport and f.departureDate = :departureDate
            """)
    Page<Flight> findFlightsByDepartureAirportAndDepartureDate(String departureAirport, LocalDate departureDate, Pageable pageable);

    /**
     * Finds flights arriving at a specific airport on a specific date.
     *
     * @param arrivalAirport The arrival airport entity.
     * @param localDate      The specific date of arrival.
     * @param pageable       The pagination information.
     * @return A page of flights arriving at the specified airport on the specified date.
     */
    @Query("""
            from Flight where arrivalAirport.city = :arrivalAirport and departureDate = :localDate
            """)
    Page<Flight> findFlightsByArrivalAirportAndDepartureDate(String arrivalAirport, LocalDate localDate, Pageable pageable);


    /**
     * Finds flights between specific airports on a specific date.
     *
     * @param departureAirport The departure airport entity.
     * @param arrivalAirport   The arrival airport entity.
     * @param departureDate    The specific date of departure.
     * @param pageable         The pagination information.
     * @return A page of flights meeting the specified criteria.
     */
    @Query("""
            from Flight where departureAirport.city = :departureAirport and arrivalAirport.city = :arrivalAirport
            and departureDate = :departureDate
            """)
    Page<Flight> findFlightsByFromAndToAndFromDate(String departureAirport, String arrivalAirport, LocalDate departureDate, Pageable pageable);

    /**
     * Finds the cheapest flights within a date range between specific airports.
     *
     * @param departureAirport The departure airport.
     * @param arrivalAirport   The arrival airport.
     * @param startDate        The start date of the range.
     * @param endDate          The end date of the range.
     * @param pageable         The pagination information.
     * @return A page of the cheapest flights within the specified date range and airports.
     */
    @Query("""
            from Flight where departureAirport.city = :departureAirport and arrivalAirport.city = :arrivalAirport
            and departureDate between :startDate and :endDate order by price asc
            """)
    Page<Flight> findCheapestFlightsWithinRange(String departureAirport, String arrivalAirport, LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Finds flights in or out of a city within a date range.
     *
     * @param city      The city of interest.
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @param pageable  The pagination information.
     * @return A page of flights in or out of the specified city within the date range.
     */
    @Query("""
            from Flight where (departureAirport.city = :p_city or arrivalAirport.city = :p_city)
            and departureDate between :p_startDate and :p_endDate
            """)
    Page<Flight> findFlightsCityDateRange(@Param("p_city") String city, @Param("p_startDate") LocalDate startDate,
                                          @Param("p_endDate") LocalDate endDate,
                                          Pageable pageable);

    /**
     * Finds flights between specific airports within a date and price range.
     *
     * @param departureAirport The city of the departure airport.
     * @param arrivalAirport   The city of the arrival airport.
     * @param startDate        The start date of the range.
     * @param endDate          The end date of the range.
     * @param minPrice         The minimum price of the flights.
     * @param maxPrice         The maximum price of the flights.
     * @param pageable         The pagination information.
     * @return A page of flights matching the specified criteria.
     */
    @Query("""
            from Flight where departureAirport.city = :p_departureAirport
            and arrivalAirport.city = :p_arrivalAirport
            and departureDate between :p_startDate and :p_endDate
            and price between :p_minPrice and :p_maxPrice
            """)
    Page<Flight> findFlightsByDepartureDateBetweenAndPriceRange(@Param("p_departureAirport") String departureAirport, @Param("p_arrivalAirport") String arrivalAirport,
                                                                @Param("p_startDate") LocalDate startDate, @Param("p_endDate") LocalDate endDate,
                                                                @Param("p_minPrice") double minPrice, @Param("p_maxPrice") double maxPrice,
                                                                Pageable pageable);


    /**
     * Finds flights by departure and arrival airports, and between departure and return dates, within a price range.
     *
     * @param departureAirport The city of the departure airport.
     * @param arrivalAirport   The city of the arrival airport.
     * @param startDate        The start date for the departure or return.
     * @param endDate          The end date for the departure or return.
     * @param minPrice         The minimum price of the flights.
     * @param maxPrice         The maximum price of the flights.
     * @param pageable         The pagination information.
     * @return A page of flights matching the specified criteria.
     */
    @Query("""
            from Flight where
            departureAirport.city = :p_departureAirport and arrivalAirport.city = :p_arrivalAirport
            and ((departureDate between :p_startDate and :p_endDate) or (returnDate between :p_startDate and :p_endDate))
            and price between :p_minPrice and :p_maxPrice
            """)
    Page<Flight> findByAllAirportAndAllDateAndPriceBetween(@Param("p_departureAirport") String departureAirport, @Param("p_arrivalAirport") String arrivalAirport,
                                                           @Param("p_startDate") LocalDate startDate, @Param("p_endDate") LocalDate endDate,
                                                           @Param("p_minPrice") double minPrice, @Param("p_maxPrice") double maxPrice,
                                                           Pageable pageable);
}
