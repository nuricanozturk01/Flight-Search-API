package nuricanozturk.dev.service.search.flight.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FlightResponseDTO(
        FlightInfoDTO departure,
        @JsonProperty("return")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        FlightInfoDTO returnFlight
)
{

}
