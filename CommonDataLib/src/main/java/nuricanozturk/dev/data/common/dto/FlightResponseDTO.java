package nuricanozturk.dev.data.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record FlightResponseDTO(
        FlightInfoDTO departure,
        @JsonProperty("return")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Optional<FlightInfoDTO> returnFlight
)
{

}
