package nuricanozturk.dev.service.search.flight.mapper;

import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.dto.FlightInfoDTO;
import nuricanozturk.dev.service.search.flight.dto.FlightsInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(implementationName = "FlightMapperImpl", componentModel = "spring")
public interface IFlightMapper
{
    @Mappings({
            @Mapping(target = "flightNumber", source = "flight.id"),
            @Mapping(target = "from", source = "flight.departureAirport.city"),
            @Mapping(target = "to", source = "flight.arrivalAirport.city"),
    })
    FlightInfoDTO toFlightInfoDTO(Flight flight);

    default FlightsInfoDTO toFlightsInfoDTO(List<FlightInfoDTO> flights)
    {
        return new FlightsInfoDTO(flights);
    }
}
