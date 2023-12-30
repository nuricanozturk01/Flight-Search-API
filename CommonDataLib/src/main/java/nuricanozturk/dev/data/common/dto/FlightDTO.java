package nuricanozturk.dev.data.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class FlightDTO
{
    @JsonProperty("flight_number")
    private UUID id;
    @JsonProperty("departure_airport")
    private String departureAirport;
    @JsonProperty("arrival_airport")
    private String arrivalAirport;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @JsonProperty("departure_date")
    private LocalDate departureDate;

    @JsonFormat(pattern = "kk:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @JsonFormat(pattern = "kk:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonProperty("return_time")
    private LocalTime returnTime;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @JsonProperty("return_date")
    private LocalDate returnDate;
    private double price;
    @JsonProperty("return_flight")
    private FlightDTO returnFlight;


    public FlightDTO()
    {
    }


    public FlightDTO getReturnFlight()
    {
        return returnFlight;
    }

    public void setReturnFlight(FlightDTO returnFlight)
    {
        this.returnFlight = returnFlight;
    }

    public String getDepartureAirport()
    {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport)
    {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport()
    {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport)
    {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDate getDepartureDate()
    {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate)
    {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate)
    {
        this.returnDate = returnDate;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public LocalTime getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public LocalTime getReturnTime()
    {
        return returnTime;
    }

    public void setReturnTime(LocalTime returnTime)
    {
        this.returnTime = returnTime;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        var str = new StringBuilder();
        str.append("Departure Airport: ").append(departureAirport).append("\n");
        str.append("Arrival Airport: ").append(arrivalAirport).append("\n");
        str.append("Departure Date: ").append(departureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        str.append("Departure Time: ").append(departureTime.format(DateTimeFormatter.ofPattern("kk:mm:ss"))).append("\n");
        str.append("Return Date: ").append(returnDate != null ? returnDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A").append("\n");
        str.append("Return Time: ").append(returnTime != null ? returnTime.format(DateTimeFormatter.ofPattern("kk:mm:ss")) : "N/A").append("\n");
        str.append("Price: ").append(price).append("\n");
        if (returnFlight != null)
            str.append("Return Flight: ").append("\n").append(returnFlight);
        return str.toString();
    }
}
