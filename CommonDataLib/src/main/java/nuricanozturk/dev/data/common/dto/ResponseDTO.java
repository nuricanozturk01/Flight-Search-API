package nuricanozturk.dev.data.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer page,
        @JsonProperty("total_page")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer totalPage,
        @JsonProperty("total_element")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer totalElement,
        String message,
        Object data)
{
}
