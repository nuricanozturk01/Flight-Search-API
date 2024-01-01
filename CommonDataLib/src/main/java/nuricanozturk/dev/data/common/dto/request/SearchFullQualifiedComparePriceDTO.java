package nuricanozturk.dev.data.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Schema(example = "{\n" +
        "  \"arrival_airport\": \"ISTANBUL\",\n" +
        "  \"departure_airport\": \"ANKARA\",\n" +
        "  \"departure_date\": \"17/01/2024\",\n" +
        "  \"return_date\": \"24/01/2024\",\n" +
        "  \"page\": 1,\n" +
        "  \"min_price\": 100,\n" +
        "  \"max_price\": 10000\n" +
        "}")
public record SearchFullQualifiedComparePriceDTO(
        @JsonProperty(value = "arrival_airport", required = true)
        String arrivalAirport,
        @JsonProperty(value = "departure_airport", required = true)
        String departureAirport,
        @JsonProperty(value = "departure_date", required = true)
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        LocalDate departureDate,
        @JsonProperty(value = "return_date")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        Optional<LocalDate> returnDate,
        int page,
        @JsonProperty(value = "min_price")
        double minPrice,
        @JsonProperty(value = "max_price")
        double maxPrice)
{

    @Override
    public String toString()
    {
        var str = new StringBuilder();
        str.append("Departure Airport: ").append(departureAirport).append("\n");
        str.append("Arrival Airport: ").append(arrivalAirport).append("\n");
        str.append("Departure Date: ").append(departureDate
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        str.append("Return Date: ").append(returnDate.map(localDate -> localDate
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("N/A")).append("\n");
        str.append("Page: ").append(page).append("\n");
        return str.toString();
    }
}
