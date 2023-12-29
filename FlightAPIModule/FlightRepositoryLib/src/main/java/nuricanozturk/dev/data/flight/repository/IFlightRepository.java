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

@Repository
public interface IFlightRepository extends JpaRepository<Flight, UUID>
{
    @Query("from Flight where arrivalAirport.city = :p_arrivalAirport")
    Page<Flight> findFlightsByArrivalAirport(@Param("p_arrivalAirport") String p_arrivalAirport, Pageable pageable);

    @Query("from Flight where departureAirport.city = :p_departureAirport")
    Page<Flight> findFlightsByDepartureAirport(@Param("p_departureAirport") String p_departureAirport, Pageable pageable);

    @Query("from Flight where arrivalAirport.city = :p_arrivalAirport and departureAirport.city = :p_departureAirport")
    Page<Flight> findFlightsByArrivalAirportAndDepartureAirport(@Param("p_arrivalAirport") String p_arrivalAirport,
                                                                @Param("p_departureAirport") String p_departureAirport,
                                                                Pageable pageable);

    @Query("""
            from Flight where
            arrivalAirport.city = :p_arrivalAirport
            and departureAirport.city = :p_departureAirport
            and departureDate = :p_departureDate
            and returnDate = :p_returnDate
            """)
    Page<Flight> findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween(@Param("p_arrivalAirport") String p_arrivalAirport,
                                                                                                    @Param("p_departureAirport") String p_departureAirport,
                                                                                                    @Param("p_departureDate") LocalDate p_departureDate,
                                                                                                    @Param("p_returnDate") LocalDate p_returnDate,
                                                                                                    Pageable pageable);

    @Query("""
            from Flight where arrivalAirport.city = :p_arrivalAirport and departureAirport.city = :p_departureAirport
            and departureDate = :p_departureDate\s
            """)
    Page<Flight> findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween(@Param("p_arrivalAirport") String p_arrivalAirport,
                                                                                       @Param("p_departureAirport") String p_departureAirport,
                                                                                       @Param("p_departureDate") LocalDate p_departureDate,
                                                                                       Pageable pageable);
}
