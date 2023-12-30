package nuricanozturk.dev.service.search.flight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public record FlightInfoDTO(
        @JsonProperty("departure_airport")
        String departureAirport,
        @JsonProperty("arrival_airport")
        String arrivalAirport,
        @JsonProperty("departure_date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate departureDate,
        @JsonProperty("departure_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss")
        LocalTime departureTime,
        @JsonProperty("return_date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        LocalDate returnDate,
        @JsonProperty("return_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        LocalTime returnTime,
        @JsonProperty("return_flight")
        @JsonIgnore
        FlightDTO returnFlight,
        double price)
{
}
