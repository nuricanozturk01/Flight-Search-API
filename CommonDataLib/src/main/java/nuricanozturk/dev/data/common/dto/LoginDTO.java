package nuricanozturk.dev.data.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "{\n" +
        "  \"username\": \"nuricanozturk\",\n" +
        "  \"password\": \"pass123\"\n" +
        "}")
public record LoginDTO(String username, String password)
{
}