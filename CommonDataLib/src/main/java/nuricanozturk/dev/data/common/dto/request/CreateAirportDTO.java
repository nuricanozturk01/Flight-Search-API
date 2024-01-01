package nuricanozturk.dev.data.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "{\n" +
        "  \"city\": \"IZMIR\"\n" +
        "}")
public record CreateAirportDTO(String city)
{
}
