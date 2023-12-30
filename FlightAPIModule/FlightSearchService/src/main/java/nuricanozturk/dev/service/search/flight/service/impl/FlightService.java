package nuricanozturk.dev.service.search.flight.service.impl;

import callofproject.dev.library.exception.ISupplier;
import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.dto.FlightInfoDTO;
import nuricanozturk.dev.service.search.flight.dto.FlightResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.FlightsResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.ResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.request.SearchFullQualifiedComparePriceDTO;
import nuricanozturk.dev.service.search.flight.dto.request.SearchFullQualifiedDTO;
import nuricanozturk.dev.service.search.flight.mapper.IFlightMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;
import static callofproject.dev.util.stream.StreamUtil.toList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static nuricanozturk.dev.service.search.flight.util.StringNormalization.convert;

@RestController
@RequestMapping("/api/flight")
public class FlightService
{
    private final FlightServiceHelper m_flightServiceHelper;
    private final IFlightMapper m_flightMapper;

    public FlightService(FlightServiceHelper flightServiceHelper, IFlightMapper flightMapper)
    {
        m_flightServiceHelper = flightServiceHelper;
        m_flightMapper = flightMapper;
    }


    public Page<Flight> findFlightsByArrivalAirport(String arrivalAirport, int page)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightsByArrivalAirport(convert(arrivalAirport), page),
                "FlightServiceHelper::findFlightsByArrivalAirport");
    }


    public Page<Flight> findFlightsByDepartureAirport(String departureAirport, int page)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureAirport(convert(departureAirport), page),
                "FlightServiceHelper::findFlightsByDepartureAirport");
    }

    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirport(String departureAirport, String arrivalAirport, int page)
    {
        var flights = m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirport(convert(arrivalAirport), convert(departureAirport), page);

        var flightsDTO = doForDataService(() -> m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO)),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureAirport");

        return new ResponseDTO(page, flights.getTotalPages(), flightsDTO.flights().size(), "Success", flightsDTO);
    }


    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween(SearchFullQualifiedDTO dto)
    {
        var arrivalAirport = convert(dto.arrivalAirport());
        var departureAirport = convert(dto.departureAirport());

        if (dto.returnDate().isEmpty())
            return findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween(arrivalAirport, departureAirport, dto.departureDate(), dto.page());

        var flights = m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween
                (arrivalAirport, departureAirport, dto.departureDate(), dto.returnDate().orElse(null), dto.page());

        var flightResponseDTO = doForDataService(() -> m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO)),
                "FlightService::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween");

        return new ResponseDTO(dto.page(), flights.getTotalPages(), flightResponseDTO.flights().size(), "Success", flightResponseDTO);
    }

    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween(String arrivalAirport, String departureAirport,
                                                                                             LocalDate departureDate, int page)
    {
        arrivalAirport = convert(arrivalAirport);
        departureAirport = convert(departureAirport);

        var flights = m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween
                (arrivalAirport, departureAirport, departureDate, page);

        var flightsDTO = doForDataService(() -> m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO)),
                "FlightService::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween");

        var flightResponseDTO = new FlightsResponseDTO(flightsDTO.flights().stream().map(f -> new FlightResponseDTO(f, empty())).toList());

        return new ResponseDTO(page, flights.getTotalPages(), flightsDTO.flights().size(), "Success", flightResponseDTO);
    }

    public Optional<Flight> findFlightById(UUID id)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightById(id),
                "FlightService::findFlightById");
    }


    /* public ResponseDTO findFlightsByDepartureAirportAndArrivalAirportAndDepartureDateBetween(Airport departureAirport, Airport arrivalAirport,
                                                                                              LocalDate startDate, LocalDate endDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureAirportAndArrivalAirportAndDepartureDateBetween(departureAirport, arrivalAirport, startDate, endDate, pageable),
                 "FlightService::findFlightsByDepartureAirportAndArrivalAirportAndDepartureDateBetween");
     }


     public ResponseDTO findFlightsByDepartureDateAndReturnDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureDateAndReturnDateBetween(startDate, endDate, pageable),
                 "FlightService::findFlightsByDepartureDateAndReturnDateBetween");
     }


     public ResponseDTO findFlightsByDepartureDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureDateBetween(startDate, endDate, pageable),
                 "FlightService::findFlightsByDepartureDateBetween");
     }


     public ResponseDTO findFlightsByDepartureAirportAndDepartureDateBetween(Airport departureAirport, LocalDate startDate, LocalDate endDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDateBetween(departureAirport, startDate, endDate, pageable),
                 "FlightService::findFlightsByDepartureAirportAndDepartureDateBetween");
     }


     public ResponseDTO findFlightsByPriceBetween(double minPrice, double maxPrice, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByPriceBetween(minPrice, maxPrice, pageable),
                 "FlightService::findFlightsByPriceBetween");
     }


     public ResponseDTO findFlightsByDepartureAirportAndDepartureDate(Airport departureAirport, LocalDate localDate)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDate(departureAirport, localDate),
                 "FlightService::findFlightsByDepartureAirportAndDepartureDate");
     }


     public ResponseDTO findFlightsByArrivalAirportAndDepartureDate(Airport arrivalAirport, LocalDate localDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureDate(arrivalAirport, localDate, pageable),
                 "FlightService::findFlightsByArrivalAirportAndDepartureDate");
     }


     public ResponseDTO findFlightsByDepartureAirportAndArrivalAirportAndDepartureDate(Airport departureAirport, Airport arrivalAirport, LocalDate departureDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureAirportAndArrivalAirportAndDepartureDate(departureAirport, arrivalAirport, departureDate, pageable),
                 "FlightService::findFlightsByDepartureAirportAndArrivalAirportAndDepartureDate");
     }


     public ResponseDTO findCheapestFlightsWithinRange(Airport departureAirport, Airport arrivalAirport, LocalDate startDate, LocalDate endDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findCheapestFlightsWithinRange(departureAirport, arrivalAirport, startDate, endDate, pageable),
                 "FlightService::findCheapestFlightsWithinRange");
     }


     public ResponseDTO findFlightsCityDateRange(String city, LocalDate startDate, LocalDate endDate, Pageable pageable)
     {
         var flights = doForDataService(() -> m_flightServiceHelper.findFlightsCityDateRange(city, startDate, endDate, pageable),
                 "FlightService::findFlightsCityDateRange");
     }

 */
    public ResponseDTO findFlightsByDateAndPriceRange(String departureAirport, String arrivalAirport, LocalDate startDate, LocalDate endDate,
                                                      double minPrice, double maxPrice, int page)
    {
        var flights = doForDataService(() -> m_flightServiceHelper.findAllByDepartureDateAndPriceRange(departureAirport, arrivalAirport,
                startDate, endDate, minPrice, maxPrice, page), "FlightService::findFlightsByDateAndPriceRange");

    }


    public ResponseDTO findByAirportAndDepartureDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findByAirportAndDepartureDateAndPriceBetween(
                dto.departureAirport(), dto.arrivalAirport(),
                dto.departureDate(), dto.returnDate().get(),
                dto.minPrice(), dto.maxPrice(), dto.page());

        var flights = doForDataService(flightSupplier, "FlightService::findByAirportAndDepartureDateAndPriceBetween");

        var flightsInfo = m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO));

        var flightsResponseDTO = new FlightsResponseDTO(flightsInfo.flights().stream().map(this::generateFlightResponseDTO).toList());

        return new ResponseDTO(dto.page(), flights.getTotalPages(), flightsResponseDTO.getFlights().size(), "Success", flightsResponseDTO);
    }

    private FlightResponseDTO generateFlightResponseDTO(FlightInfoDTO flightInfoDTO)
    {
        if (flightInfoDTO.returnFlight() == null)
            return new FlightResponseDTO(flightInfoDTO, empty());

        return new FlightResponseDTO(flightInfoDTO, of(m_flightMapper.toFlightInfoDTO(flightInfoDTO.returnFlight())));
    }

}
