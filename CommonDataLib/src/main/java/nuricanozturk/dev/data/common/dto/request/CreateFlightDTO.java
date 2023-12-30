package nuricanozturk.dev.data.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import nuricanozturk.dev.data.flight.entity.Flight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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
        Optional<Flight> returnFlight,

        double price)
{
}
