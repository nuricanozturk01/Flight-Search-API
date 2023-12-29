package nuricanozturk.dev.data.flight.dal;

import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Customer;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.data.flight.repository.IAirportRepository;
import nuricanozturk.dev.data.flight.repository.ICustomerRepository;
import nuricanozturk.dev.data.flight.repository.IFlightRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static callofproject.dev.library.exception.util.CopDataUtil.doForRepository;

@Component
@Lazy
public class FlightServiceHelper
{
    private final IAirportRepository m_airportRepository;
    private final IFlightRepository m_flightRepository;
    private final ICustomerRepository m_customerRepository;

    public FlightServiceHelper(IAirportRepository airportRepository, IFlightRepository flightRepository, ICustomerRepository customerRepository)
    {
        m_airportRepository = airportRepository;
        m_flightRepository = flightRepository;
        m_customerRepository = customerRepository;
    }

    public Page<Flight> findFlightsByArrivalAirport(String arrivalAirport, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByArrivalAirport(arrivalAirport, pageable),
                "FlightServiceHelper::findFlightsByArrivalAirport");
    }


    public Page<Flight> findFlightsByDepartureAirport(String departureAirport, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByDepartureAirport(departureAirport, pageable),
                "FlightServiceHelper::findFlightsByDepartureAirport");
    }

    public Page<Flight> findFlightsByArrivalAirportAndDepartureAirport(String arrivalAirport, String departureAirport, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport, pageable),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureAirport");
    }


    public Page<Flight> findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween(String arrivalAirport,
                                                                                                           String departureAirport,
                                                                                                           LocalDate departureDate,
                                                                                                           LocalDate returnDate,
                                                                                                           int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween
                        (arrivalAirport, departureAirport, departureDate, returnDate, pageable),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween");
    }

    public Page<Flight> findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween(String arrivalAirport,
                                                                                              String departureAirport,
                                                                                              LocalDate departureDate,
                                                                                              int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween
                        (arrivalAirport, departureAirport, departureDate, pageable),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween");
    }

    public Optional<Flight> findFlightById(UUID id)
    {
        return doForRepository(() -> m_flightRepository.findById(id),
                "FlightServiceHelper::findFlightById");
    }

    public Optional<Airport> findAirportByCity(String city)
    {
        return doForRepository(() -> m_airportRepository.findByCity(city),
                "FlightServiceHelper::findAirportByCity");
    }

    public Optional<Customer> findCustomerByUsername(String username)
    {
        return doForRepository(() -> m_customerRepository.findByUsername(username),
                "FlightServiceHelper::findCustomerByUsername");
    }

    public Airport saveAirport(String airport)
    {
        return doForRepository(() -> m_airportRepository.save(new Airport(airport)), "FlightServiceHelper::saveAirport");
    }
}
