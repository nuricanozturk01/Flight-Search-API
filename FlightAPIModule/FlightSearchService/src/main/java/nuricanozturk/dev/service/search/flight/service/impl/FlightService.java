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
        checkSearchFullQualifiedDTO(dto);
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToLocationAndDate(convert(dto.arrivalAirport()), convert(dto.departureAirport()), dto.departureDate(), dto.returnDate().get(), dto.page());

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToLocationAndDate", dto.page());
    }

    private void checkSearchFullQualifiedDTO(SearchFullQualifiedDTO dto)
    {
        if (dto.returnDate().isEmpty())
            throw new DataServiceException("Return date cannot be empty");
        if (dto.departureDate().isAfter(dto.returnDate().get()))
                throw new DataServiceException("Departure date cannot be after return date");
        if (dto.departureDate().isAfter(dto.returnDate().get()))
            throw new DataServiceException("Departure date cannot be after return date");
    }

    public ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndDateBetween(convert(from), convert(to), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndDateBetween", page);
    }


    public ResponseDTO findFlightsByFromAndToAndDateBetween(String from, String to, String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndDateBetween(convert(from), convert(to), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndDateBetween", page);
    }


    public ResponseDTO findFlightsByDepartureDateBetween(String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureDateBetween(parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureDateBetween", page);
    }


    public ResponseDTO findFlightsByDepartureAirportAndDepartureDateBetween(String from, String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDateBetween(convert(from), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureAirportAndDepartureDateBetween", page);
    }


    public ResponseDTO findFlightsByPriceBetween(double minPrice, double maxPrice, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByPriceBetween(minPrice, maxPrice, page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByPriceBetween", page);
    }


    public ResponseDTO findFlightsByDepartureAirportAndDepartureDate(String from, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByDepartureAirportAndDepartureDate(convert(from), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByDepartureAirportAndDepartureDate", page);
    }


    public ResponseDTO findFlightsByArrivalAirportAndDepartureDate(String arrivalAirport, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByArrivalAirportAndDepartureDate(convert(arrivalAirport), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByArrivalAirportAndDepartureDate", page);
    }


    public ResponseDTO findFlightsByFromAndToAndFromDate(String from, String to, String date, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsByFromAndToAndFromDate(convert(from), convert(to), parseDate(date), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsByFromAndToAndFromDate", page);
    }


    public ResponseDTO findCheapestFlightsWithinRange(String from, String to, String start, String end, int page)
    {
        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findCheapestFlightsWithinRange(convert(from), convert(to), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findCheapestFlightsWithinRange", page);
    }


    public ResponseDTO findFlightsCityDateRange(String city, String start, String end, int page)
    {   //city is departure city or arrival city

        ISupplier<Page<Flight>> flightSupplier = () -> m_flightServiceHelper.findFlightsCityDateRange(convert(city), parseDate(start), parseDate(end), page);

        return toFlightsResponseDTO(flightSupplier, "FlightService::findFlightsCityDateRange", page);
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


    private ResponseDTO toFlightsResponseDTO(ISupplier<Page<Flight>> supplier, String msg, int page)
    {
        var flights = doForDataService(supplier, msg);

        var flightsInfo = m_flightMapper.toFlightsInfoDTO(toList(flights.getContent(), m_flightMapper::toFlightInfoDTO));

        var flightsResponseDTO = new FlightsResponseDTO(flightsInfo.flights().stream().map(this::generateFlightResponseDTO).toList());

        return new ResponseDTO(page, flights.getTotalPages(), flightsResponseDTO.getFlights().size(), "Success", flightsResponseDTO);
    }

    private LocalDate parseDate(String date)
    {
        return doForDataService(() -> LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), "FlightService::parseDate");
    }

    private String toDateString(LocalDate date)
    {
        return doForDataService(() -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "FlightService::toDateString");
    }
}