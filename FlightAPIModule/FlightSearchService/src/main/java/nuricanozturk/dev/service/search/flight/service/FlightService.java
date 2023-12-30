package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.dto.ResponseDTO;
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
        return doForDataService(() -> m_flightServiceHelper.findFlightsByArrivalAirport(arrivalAirport, page),
                "FlightServiceHelper::findFlightsByArrivalAirport");
    }


    public Page<Flight> findFlightsByDepartureAirport(String departureAirport, int page)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightsByDepartureAirport(departureAirport, page),
                "FlightServiceHelper::findFlightsByDepartureAirport");
    }

    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirport(String departureAirport, String arrivalAirport, int page)
    {
        var flights = m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport, page);
        var flightsDTO = doForDataService(() -> m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO)),
                "FlightServiceHelper::findFlightsByArrivalAirportAndDepartureAirport");

        return new ResponseDTO(page, flights.getTotalPages(), flightsDTO.flights().size(), "Success", flightsDTO);
    }


    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween(SearchFullQualifiedDTO dto)
    {
        System.out.println(dto);
        if (dto.returnDate() == null)
            return findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween(dto.arrivalAirport(), dto.departureAirport(), dto.departureDate(), dto.page());
        System.out.println("here1");
        var flights = m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween
                (dto.arrivalAirport(), dto.departureAirport(), dto.departureDate(), dto.returnDate(), dto.page());
        System.out.println(flights.getContent().size());
        var flightResponseDTO = doForDataService(() -> m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO)),
                "FlightService::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateAndReturnDateBetween");

        return new ResponseDTO(dto.page(), flights.getTotalPages(), flightResponseDTO.flights().size(), "Success", flightResponseDTO);
    }

    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween(String arrivalAirport, String departureAirport,
                                                                                             LocalDate departureDate, int page)
    {
        var flights = m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween
                (arrivalAirport, departureAirport, departureDate, page);

        var flightsDTO = doForDataService(() -> m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO)),
                "FlightService::findFlightsByArrivalAirportAndDepartureAirportAndDepartureDateBetween");

        return new ResponseDTO(page, flights.getTotalPages(), flightsDTO.flights().size(), "Success", flightsDTO);
    }

    public Optional<Flight> findFlightById(UUID id)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightById(id),
                "FlightServiceHelper::findFlightById");
    }
}
