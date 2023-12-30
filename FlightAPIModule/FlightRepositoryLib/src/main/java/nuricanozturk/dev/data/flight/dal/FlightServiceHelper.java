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
import java.util.List;
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


    public Flight saveFlight(Flight flight)
    {
        return doForRepository(() -> m_flightRepository.save(flight), "FlightServiceHelper::saveFlight");
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


    public Page<Flight> findFlightsByFromAndToLocationAndDate(String arrivalAirport, String departureAirport,
                                                              LocalDate departureDate, LocalDate returnDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByFromAndToLocationAndDate(arrivalAirport, departureAirport, departureDate, returnDate, pageable),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween");
    }

    public Page<Flight> findFlightsByFromAndToAndDateBetween(String arrivalAirport, String departureAirport,
                                                             LocalDate departureDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findFlightsByFromAndToAndDateBetween(arrivalAirport, departureAirport, departureDate, pageable),
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
        if (m_airportRepository.findByCity(airport).isPresent())
            return m_airportRepository.findByCity(airport).get();

        return doForRepository(() -> m_airportRepository.save(new Airport(airport)), "FlightServiceHelper::saveAirport");
    }

    public Airport saveAirport(Airport airport)
    {
        return doForRepository(() -> m_airportRepository.save(airport), "FlightServiceHelper::saveAirport");
    }

    public void deleteFlightById(UUID flightId)
    {
        doForRepository(() -> m_flightRepository.deleteById(flightId), "FlightServiceHelper::deleteFlightById");
    }

    public void deleteAirportById(UUID airportId)
    {
        doForRepository(() -> m_airportRepository.deleteById(airportId), "FlightServiceHelper::deleteAirportById");
    }

    public Optional<Airport> findAirportById(UUID airportId)
    {
        return doForRepository(() -> m_airportRepository.findById(airportId), "FlightServiceHelper::findAirportById");
    }

    public Page<Flight> findAllFlights(int page)
    {
        return doForRepository(() -> m_flightRepository.findAll(PageRequest.of(page - 1, 15)), "FlightServiceHelper::findAllFlights");
    }

    public Page<Airport> findAllAirports(int page)
    {
        return doForRepository(() -> m_airportRepository.findAll(PageRequest.of(page - 1, 15)), "FlightServiceHelper::findAllAirports");
    }

    public void saveAllFlights(List<Flight> flights)
    {
        doForRepository(() -> m_flightRepository.saveAll(flights), "FlightServiceHelper::saveAllFlights");
    }

    public Page<Flight> findFlightsByFromAndToAndDateBetween(String departureAirport, String arrivalAirport,
                                                             LocalDate startDate, LocalDate endDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByDepartureAirportAndArrivalAirportAndDepartureDateBetween(departureAirport, arrivalAirport, startDate, endDate, pageable),
                "FlightServiceHelper::findFlightsByDepartureAirportAndArrivalAirportAndDepartureDateBetween");
    }


    public Page<Flight> findFlightsByDepartureDateBetween(LocalDate startDate, LocalDate endDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByDepartureDateBetween(startDate, endDate, pageable),
                "FlightServiceHelper::findFlightsByDepartureDateBetween");
    }


    public Page<Flight> findFlightsByDepartureAirportAndDepartureDateBetween(String departureAirport, LocalDate startDate, LocalDate endDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByDepartureAirportAndDepartureDateBetween(departureAirport, startDate, endDate, pageable),
                "FlightServiceHelper::findFlightsByDepartureAirportAndDepartureDateBetween");
    }


    public Page<Flight> findFlightsByPriceBetween(double minPrice, double maxPrice, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByPriceBetween(minPrice, maxPrice, pageable),
                "FlightServiceHelper::findFlightsByPriceBetween");
    }


    public Page<Flight> findFlightsByDepartureAirportAndDepartureDate(String departureAirport, LocalDate localDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByDepartureAirportAndDepartureDate(departureAirport, localDate, pageable),
                "FlightServiceHelper::findFlightsByDepartureAirportAndDepartureDate");
    }


    public Page<Flight> findFlightsByArrivalAirportAndDepartureDate(String arrivalAirport, LocalDate localDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByArrivalAirportAndDepartureDate(arrivalAirport, localDate, pageable),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureDate");
    }


    public Page<Flight> findFlightsByFromAndToAndFromDate(String departureAirport, String arrivalAirport, LocalDate departureDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByFromAndToAndFromDate(departureAirport, arrivalAirport, departureDate, pageable),
                "FlightServiceHelper::findFlightsByDepartureAirportAndArrivalAirportAndDepartureDate");
    }


    public Page<Flight> findCheapestFlightsWithinRange(String departureAirport, String arrivalAirport, LocalDate startDate, LocalDate endDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findCheapestFlightsWithinRange(departureAirport, arrivalAirport, startDate, endDate, pageable),
                "FlightServiceHelper::findCheapestFlightsWithinRange");
    }


    public Page<Flight> findFlightsCityDateRange(String city, LocalDate startDate, LocalDate endDate, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsCityDateRange(city, startDate, endDate, pageable),
                "FlightServiceHelper::findFlightsCityDateRange");
    }


    public Page<Flight> findAllByDepartureDateAndPriceRange(String departureAirport, String arrivalAirport, LocalDate startDate, LocalDate endDate,
                                                            double minPrice, double maxPrice, int page)
    {
        var pageable = PageRequest.of(page - 1, 15);
        return doForRepository(() -> m_flightRepository.findFlightsByDepartureDateBetweenAndPriceRange(departureAirport, arrivalAirport, startDate, endDate, minPrice, maxPrice, pageable),
                "FlightServiceHelper::findFlightsByDateAndPriceRange");
    }


    public Page<Flight> findByAirportAndDepartureDateAndPriceBetween(String departureAirport, String arrivalAirport,
                                                                     LocalDate startDate, LocalDate endDate,
                                                                     double minPrice, double maxPrice,
                                                                     int page)
    {
        var pageable = PageRequest.of(page - 1, 15);

        return doForRepository(() -> m_flightRepository.findByDepartureAirportCityAndArrivalAirportCityAndDepartureDateBetweenAndReturnDateBetweenAndPriceBetween
                        (departureAirport, arrivalAirport, startDate, endDate, minPrice, maxPrice, pageable),
                "FlightServiceHelper::findByDepartureAirportCityAndArrivalAirportCityAndDepartureDateBetweenAndReturnDateBetweenAndPriceBetween");
    }
}
