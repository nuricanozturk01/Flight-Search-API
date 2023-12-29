package nuricanozturk.dev.data.flight.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    private LocalDateTime departureDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;
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

        public Builder withDepartureDate(LocalDateTime departureDate)
        {
            m_flight.departureDate = departureDate;
            return this;
        }

        public Builder withReturnDate(LocalDateTime returnDate)
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

    public LocalDateTime getDepartureDate()
    {
        return departureDate;
    }

    public LocalDateTime getReturnDate()
    {
        return returnDate;
    }

    public double getPrice()
    {
        return price;
    }
}
