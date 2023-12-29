package nuricanozturk.dev.service.search.flight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseDTO(int page,
                          @JsonProperty("total_page")
                          int totalPage,
                          @JsonProperty("total_element")
                          int totalElement,
                          String message,
                          Object data)
{
}
