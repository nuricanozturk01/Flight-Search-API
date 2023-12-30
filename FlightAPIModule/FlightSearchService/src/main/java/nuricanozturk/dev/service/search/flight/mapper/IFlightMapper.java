package nuricanozturk.dev.service.search.flight.mapper;

import nuricanozturk.dev.data.common.dto.FlightDTO;
import nuricanozturk.dev.data.common.dto.FlightInfoDTO;
import nuricanozturk.dev.data.common.dto.FlightsInfoDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper(implementationName = "FlightMapperImpl", componentModel = "spring")
public interface IFlightMapper
{
    @Mappings({
            @Mapping(target = "departureAirport", source = "flight.departureAirport.city"),
            @Mapping(target = "arrivalAirport", source = "flight.arrivalAirport.city"),
            @Mapping(target = "returnFlight.arrivalAirport", source = "flight.returnFlight.arrivalAirport.city"),
            @Mapping(target = "returnFlight.departureAirport", source = "flight.returnFlight.departureAirport.city"),
    })
    FlightInfoDTO toFlightInfoDTO(Flight flight);

    default FlightsInfoDTO toFlightsInfoDTO(List<FlightInfoDTO> flights)
    {
        return new FlightsInfoDTO(flights);
    }

    @Mappings({
            @Mapping(target = "returnTime", expression = "java(mapReturnTime(updateFlightDTO.returnTime()))"),
            @Mapping(target = "returnDate", expression = "java(mapReturnDate(updateFlightDTO.returnDate()))"),
            @Mapping(target = "departureAirport", source = "departureAirport"),
            @Mapping(target = "arrivalAirport", source = "arrivalAirport"),
            @Mapping(target = "id", source = "id")
    })
    Flight toFlight(UpdateFlightDTO updateFlightDTO, Airport departureAirport, Airport arrivalAirport, UUID id);

    default LocalTime mapReturnTime(Optional<LocalTime> returnTime)
    {
        return returnTime.orElse(null);
    }

    default LocalDate mapReturnDate(Optional<LocalDate> returnDate)
    {
        return returnDate.orElse(null);
    }


    FlightInfoDTO toFlightInfoDTO(FlightDTO flight);
}
