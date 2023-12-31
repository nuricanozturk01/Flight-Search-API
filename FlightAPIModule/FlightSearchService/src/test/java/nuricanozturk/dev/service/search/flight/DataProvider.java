package nuricanozturk.dev.service.search.flight;


import nuricanozturk.dev.data.common.dto.RegisterDTO;
import nuricanozturk.dev.data.common.dto.request.*;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Flight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static java.time.LocalDate.now;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class DataProvider
{
    private DataProvider()
    {
    }

    public static RegisterDTO createCustomer()
    {
        return new RegisterDTO("nuricanozturk", "Nuri", "Can", "OZTURK", "canozturk309@gmail.com", "pass123");
    }

    public static Airport provideIstanbulAirport()
    {
        return new Airport("IST");
    }

    public static Airport provideAnkaraAirport()
    {
        return new Airport("ANK");
    }

    public static Flight provideOneWayFlight(Airport from, Airport to)
    {
        return new Flight.Builder()
                .withReturnFlight(null)
                .withDepartureDate(LocalDate.of(2024, 1, 1))
                .withDepartureTime(LocalTime.of(10, 30, 0))
                .withReturnDate(null)
                .withReturnTime(null)
                .withDepartureAirport(from)
                .withArrivalAirport(to)
                .withPrice(100)
                .build();
    }

    public static Flight provideRoundTrapFlight(Airport from, Airport to)
    {
        var returnFlight = new Flight.Builder()
                .withReturnFlight(null)
                .withReturnDate(null)
                .withReturnTime(null)
                .withDepartureDate(LocalDate.of(2024, 2, 15))
                .withDepartureTime(LocalTime.of(12, 0, 0))
                .withDepartureAirport(from)
                .withArrivalAirport(to)
                .withPrice(100)
                .build();

        return new Flight.Builder()
                .withReturnFlight(returnFlight)
                .withDepartureDate(LocalDate.of(2024, 2, 1))
                .withDepartureTime(LocalTime.of(10, 30, 0))
                .withReturnDate(LocalDate.of(2024, 2, 15))
                .withReturnTime(LocalTime.of(12, 0, 0))
                .withDepartureAirport(to)
                .withArrivalAirport(from)
                .withPrice(100)
                .build();
    }

    public static CreateFlightDTO provideCreateFlightDTO()
    {
        return new CreateFlightDTO("IZMIR", "ANKARA", now(),
                LocalTime.now(), empty(), empty(), empty(), 100);
    }

    public static CreateAirportDTO provideCreateAirportDTO()
    {
        return new CreateAirportDTO("EDIRNE");
    }

    public static UpdateFlightDTO provideUpdateFlightDTO()
    {
        return new UpdateFlightDTO(UUID.randomUUID(), "ANTALYA", "ANKARA", now(),
                LocalTime.now(), empty(), empty(), 900);
    }

    public static UpdateAirportDTO provideUpdateAirportDTO()
    {
        return new UpdateAirportDTO(UUID.randomUUID(), "ANTALYA");
    }

    public static SearchFullQualifiedDTO provideSearchFullQualifiedDTO()
    {
        return new SearchFullQualifiedDTO("ANK", "IST", now(), of(now().plusDays(20)), 1);
    }

    public static SearchFullQualifiedComparePriceDTO provideSearchFullQualifiedComparePriceDTO()
    {
        return new SearchFullQualifiedComparePriceDTO("ANK", "IST",
                now(), of(now().plusDays(20)), 1, 100, 1000);
    }
}
