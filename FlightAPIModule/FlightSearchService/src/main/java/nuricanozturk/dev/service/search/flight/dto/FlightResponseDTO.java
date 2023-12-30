package nuricanozturk.dev.service.search.flight.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record FlightResponseDTO(
        @JsonProperty("departure_flight")
        FlightInfoDTO departure,
        @JsonProperty("return_flight")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Optional<FlightInfoDTO> returnFlight
)
{

}
