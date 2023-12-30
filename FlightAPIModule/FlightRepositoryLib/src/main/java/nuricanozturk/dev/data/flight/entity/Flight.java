package nuricanozturk.dev.data.flight.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "flight")
public class Flight
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "return_time")
    private LocalTime returnTime;

    @Column(name = "return_date")
    private LocalDate returnDate;
    private double price;

    public Flight()
    {
    }

    public static class Builder
    {
        private final Flight m_flight;

        public Builder()
        {
            m_flight = new Flight();
        }

        public Builder withDepartureAirport(Airport departureAirport)
        {
            m_flight.departureAirport = departureAirport;
            return this;
        }

        public Builder withArrivalAirport(Airport arrivalAirport)
        {
            m_flight.arrivalAirport = arrivalAirport;
            return this;
        }

        public Builder withDepartureTime(LocalTime departureTime)
        {
            m_flight.departureTime = departureTime;
            return this;
        }

        public Builder withReturnTime(LocalTime returnTime)
        {
            m_flight.returnTime = returnTime;
            return this;
        }

        public Builder withDepartureDate(LocalDate departureDate)
        {
            m_flight.departureDate = departureDate;
            return this;
        }

        public Builder withReturnDate(LocalDate returnDate)
        {
            m_flight.returnDate = returnDate;
            return this;
        }

        public Builder withPrice(double price)
        {
            m_flight.price = price;
            return this;
        }

        public Flight build()
        {
            return m_flight;
        }
    }

    public UUID getId()
    {
        return id;
    }

    public Airport getDepartureAirport()
    {
        return departureAirport;
    }

    public Airport getArrivalAirport()
    {
        return arrivalAirport;
    }

    public LocalDate getDepartureDate()
    {
        return departureDate;
    }

    public LocalDate getReturnDate()
    {
        return returnDate;
    }

    public LocalTime getDepartureTime()
    {
        return departureTime;
    }

    public LocalTime getReturnTime()
    {
        return returnTime;
    }

    public double getPrice()
    {
        return price;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public void setDepartureAirport(Airport departureAirport)
    {
        this.departureAirport = departureAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport)
    {
        this.arrivalAirport = arrivalAirport;
    }

    public void setDepartureDate(LocalDate departureDate)
    {
        this.departureDate = departureDate;
    }

    public void setDepartureTime(LocalTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public void setReturnTime(LocalTime returnTime)
    {
        this.returnTime = returnTime;
    }

    public void setReturnDate(LocalDate returnDate)
    {
        this.returnDate = returnDate;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Flight update(Flight flight, Airport departureAirport, Airport arrivalAirport)
    {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = flight.departureDate;
        this.departureTime = flight.departureTime;
        this.returnTime = flight.returnTime;
        this.returnDate = flight.returnDate;
        this.price = flight.price;
        return this;
    }
}
