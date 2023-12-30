package nuricanozturk.dev.data.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public record FlightInfoDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate departureDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss")
        LocalTime departureTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDate returnDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalTime returnTime,
        String from,
        String to,
        @JsonProperty("flight_number")
        String flightNumber)
{
}
