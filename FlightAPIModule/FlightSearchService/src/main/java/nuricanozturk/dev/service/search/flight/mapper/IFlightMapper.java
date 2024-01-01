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

/**
 * IFlightMapper is an interface for MapStruct to map between entity objects (Flight, Airport) and DTOs (FlightDTO, FlightInfoDTO, etc.).
 * This interface is responsible for converting complex domain models into simpler, flattened data transfer objects and vice versa.
 */
@Mapper(implementationName = "FlightMapperImpl", componentModel = "spring")
public interface IFlightMapper
{
    /**
     * Maps a Flight entity to a FlightInfoDTO.
     *
     * @param flight The Flight entity to map.
     * @return The mapped FlightInfoDTO.
     */
    @Mappings({
            @Mapping(target = "departureAirport", source = "flight.departureAirport.city"),
            @Mapping(target = "arrivalAirport", source = "flight.arrivalAirport.city"),
            @Mapping(target = "returnFlight.arrivalAirport", source = "flight.returnFlight.arrivalAirport.city"),
            @Mapping(target = "returnFlight.departureAirport", source = "flight.returnFlight.departureAirport.city"),
    })
    FlightInfoDTO toFlightInfoDTO(Flight flight);

    /**
     * Converts a list of FlightInfoDTOs to a FlightsInfoDTO.
     *
     * @param flights The list of FlightInfoDTOs.
     * @return The corresponding FlightsInfoDTO.
     */
    default FlightsInfoDTO toFlightsInfoDTO(List<FlightInfoDTO> flights)
    {
        return new FlightsInfoDTO(flights);
    }

    /**
     * Maps an UpdateFlightDTO and Airport entities to a Flight entity.
     *
     * @param updateFlightDTO  The DTO containing updated flight information.
     * @param departureAirport The departure Airport entity.
     * @param arrivalAirport   The arrival Airport entity.
     * @param id               The UUID of the flight.
     * @return The updated Flight entity.
     */
    @Mappings({
            @Mapping(target = "returnTime", expression = "java(mapReturnTime(updateFlightDTO.returnTime()))"),
            @Mapping(target = "returnDate", expression = "java(mapReturnDate(updateFlightDTO.returnDate()))"),
            @Mapping(target = "departureAirport", source = "departureAirport"),
            @Mapping(target = "arrivalAirport", source = "arrivalAirport"),
            @Mapping(target = "id", source = "id")
    })
    Flight toFlight(UpdateFlightDTO updateFlightDTO, Airport departureAirport, Airport arrivalAirport, UUID id);

    /**
     * Maps an Optional LocalTime to a LocalTime, returning null if the Optional is empty.
     *
     * @param returnTime The Optional LocalTime to map.
     * @return The LocalTime or null.
     */
    default LocalTime mapReturnTime(Optional<LocalTime> returnTime)
    {
        return returnTime.orElse(null);
    }

    /**
     * Maps an Optional LocalDate to a LocalDate, returning null if the Optional is empty.
     *
     * @param returnDate The Optional LocalDate to map.
     * @return The LocalDate or null.
     */
    default LocalDate mapReturnDate(Optional<LocalDate> returnDate)
    {
        return returnDate.orElse(null);
    }

    /**
     * Maps a FlightDTO to a FlightInfoDTO.
     *
     * @param flight The FlightDTO to map.
     * @return The corresponding FlightInfoDTO.
     */
    FlightInfoDTO toFlightInfoDTO(FlightDTO flight);
}
