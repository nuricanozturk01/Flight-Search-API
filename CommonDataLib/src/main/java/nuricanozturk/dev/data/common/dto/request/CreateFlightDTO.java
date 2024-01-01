package nuricanozturk.dev.data.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Schema(example = "{\n" +
        "  \"arrival_airport\": \"ISTANBUL\",\n" +
        "  \"departure_airport\": \"ANKARA\",\n" +
        "  \"departure_date\": \"17/01/2024\",\n" +
        "  \"departure_time\": \"12:00:00\",\n" +
        "  \"return_time\": \"12:00:00\",\n" +
        "  \"return_date\": \"24/01/2024\",\n" +
        "  \"return_flight\": {\n" +
        "    \"arrival_airport\": \"ANKARA\",\n" +
        "    \"departure_airport\": \"ISTANBUL\",\n" +
        "    \"departure_date\": \"24/01/2024\",\n" +
        "    \"departure_time\": \"12:00:00\",\n" +
        /*"    \"return_time\": \"12:00:00\",\n" +
        "    \"return_date\": \"2021-01-02\",\n" +
        "    \"return_flight\": null,\n" +*/
        "    \"price\": 100\n" +
        "  },\n" +
        "  \"price\": 100\n" +
        "}")
public record CreateFlightDTO(
        @JsonProperty(value = "arrival_airport", required = true)
        String arrivalAirport,
        @JsonProperty(value = "departure_airport", required = true)
        String departureAirport,
        @JsonProperty(value = "departure_date", required = true)
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        LocalDate departureDate,
        @JsonProperty(value = "departure_time", required = true)
        @JsonFormat(pattern = "kk:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalTime departureTime,
        @JsonProperty("return_time")
        @JsonFormat(pattern = "kk:mm:ss", shape = JsonFormat.Shape.STRING)
        Optional<LocalTime> returnTime,
        @JsonProperty("return_date")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        Optional<LocalDate> returnDate,

        @JsonProperty("return_flight")
        Optional<CreateFlightDTO> returnFlight,

        double price)
{
}
