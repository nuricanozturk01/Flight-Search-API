package nuricanozturk.dev.service.search.flight;

import io.swagger.v3.oas.annotations.media.ExampleObject;

@ExampleObject(name = "body", value = "{\n" +
        "  \"departure_airport\": \"IST\",\n" +
        "  \"to\": \"ESB\",\n" +
        "  \"departureDate\": \"2021-01-01\",\n" +
        "  \"returnDate\": \"2021-01-02\",\n" +
        "  \"adult\": 1,\n" +
        "  \"child\": 1,\n" +
        "  \"infant\": 1\n" +
        "}")
public @interface Body
{
}
