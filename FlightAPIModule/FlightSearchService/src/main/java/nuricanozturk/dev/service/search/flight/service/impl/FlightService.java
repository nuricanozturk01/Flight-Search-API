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

    public Optional<Flight> findFlightById(UUID id)
    {
        return doForDataService(() -> m_flightServiceHelper.findFlightById(id), "FlightService::findFlightById");
    }

    public ResponseDTO findFlightsByArrivalAirport(String arrivalAirport, int page)
    {
        ISupplier<Page<Flight>> flights = () -> m_flightServiceHelper.findFlightsByArrivalAirport(convert(arrivalAirport), page);

        return toFlightsResponseDTO(flights, "FlightService::findFlightsByArrivalAirport", page);
    }


    public ResponseDTO findFlightsByDepartureAirport(String departureAirport, int page)
    {
        ISupplier<Page<Flight>> flights = () -> m_flightServiceHelper.findFlightsByDepartureAirport(convert(departureAirport), page);

        return toFlightsResponseDTO(flights, "FlightService::findFlightsByDepartureAirport", page);
    }

    public ResponseDTO findFlightsByArrivalAirportAndDepartureAirport(String departureAirport, String arrivalAirport, int page)
    {
        ISupplier<Page<Flight>> flights = () -> m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureAirport(convert(arrivalAirport), convert(departureAirport), page);

        return toFlightsResponseDTO(flights, "FlightService::findFlightsByArrivalAirportAndDepartureAirport", page);
    }


    public ResponseDTO findFlightsByFromAndToLocationAndDate(SearchFullQualifiedDTO dto)
    {
        if (dto.returnDate().isEmpty())
            return findFlightsByFromAndToLocationAndDate(dto);

        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToLocationAndDate(convert(dto.arrivalAirport()),
                convert(dto.departureAirport()), dto.departureDate(), dto.returnDate().orElse(null), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToLocationAndDate", dto.page());
    }

    public ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, LocalDate date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndDateBetween(convert(from), convert(to), date, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndDateBetween", page);
    }


    public ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, LocalDate start, LocalDate end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndDateBetween(convert(from), convert(to), start, end, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndDateBetween", page);
    }


    public ResponseDTO findFlightsByDepartureDateBetween(LocalDate start, LocalDate end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureDateBetween(start, end, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureDateBetween", page);
    }


    public ResponseDTO findFlightsByDepartureAirportAndDepartureDateBetween(String from, LocalDate start, LocalDate end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDateBetween(convert(from), start, end, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureAirportAndDepartureDateBetween", page);
    }


    public ResponseDTO findFlightsByPriceBetween(double minPrice, double maxPrice, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByPriceBetween(minPrice, maxPrice, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByPriceBetween", page);
    }


    public ResponseDTO findFlightsByDepartureAirportAndDepartureDate(String from, LocalDate date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDate(convert(from), date, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureAirportAndDepartureDate", page);
    }


    public ResponseDTO findFlightsByArrivalAirportAndDepartureDate(String arrivalAirport, LocalDate date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureDate(convert(arrivalAirport), date, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByArrivalAirportAndDepartureDate", page);
    }


    public ResponseDTO findFlightsByFromAndToAndFromDate(String from, String to, LocalDate date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndFromDate(convert(from), convert(to), date, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndFromDate", page);
    }


    public ResponseDTO findCheapestFlightsWithinRange(String from, String to, LocalDate start, LocalDate end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findCheapestFlightsWithinRange(convert(from), convert(to), start, end, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findCheapestFlightsWithinRange", page);
    }


    public ResponseDTO findFlightsCityDateRange(String city, LocalDate start, LocalDate end, int page)
    {   //city is departure city or arrival city

        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsCityDateRange(convert(city), start, end, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsCityDateRange", page);
    }

    public ResponseDTO toFlightsResponseDTO(ISupplier<Page<Flight>> supplier, String msg, int page)
    {
        var flights = doForDataService(supplier, msg);

        var flightsInfo = m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO));

        var flightsResponseDTO = new FlightsResponseDTO(flightsInfo.flights().stream().map(this::generateFlightResponseDTO).toList());

        return new ResponseDTO(page, flights.getTotalPages(), flightsResponseDTO.getFlights().size(), "Success", flightsResponseDTO);
    }

    public ResponseDTO findByAirportAndDepartureDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findAllByDepartureDateAndPriceRange(
                convert(dto.departureAirport()), convert(dto.arrivalAirport()),
                dto.departureDate(), dto.returnDate().get(),
                dto.minPrice(), dto.maxPrice(), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findByAirportAndDepartureDateAndPriceBetween", dto.page());
    }

    private FlightResponseDTO generateFlightResponseDTO(FlightInfoDTO flightInfoDTO)
    {
        if (flightInfoDTO.returnFlight() == null)
            return new FlightResponseDTO(flightInfoDTO, empty());

        return new FlightResponseDTO(flightInfoDTO, of(m_flightMapper.toFlightInfoDTO(flightInfoDTO.returnFlight())));
    }

    public ResponseDTO findByAllAirportAndAllDateAndPriceBetween(SearchFullQualifiedComparePriceDTO dto)
    {// All means contains tuples (departureAirport, arrivalAirport, departureDate, returnDate, minPrice, maxPrice...)

        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findByAllAirportAndAllDateAndPriceBetween(
                convert(dto.departureAirport()), convert(dto.arrivalAirport()),
                dto.departureDate(), dto.returnDate().get(),
                dto.minPrice(), dto.maxPrice(), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findByAllAirportAndAllDateAndPriceBetween", dto.page());
    }
}