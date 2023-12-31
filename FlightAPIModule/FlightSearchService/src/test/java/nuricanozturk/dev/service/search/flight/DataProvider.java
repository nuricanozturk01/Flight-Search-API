package nuricanozturk.dev.service.search.flight;


import nuricanozturk.dev.data.common.dto.FlightInfoDTO;
import nuricanozturk.dev.data.common.dto.FlightResponseDTO;
import nuricanozturk.dev.data.common.dto.RegisterDTO;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Flight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static java.util.Optional.of;

public final class DataProvider
{
    private DataProvider()
    {
    }

    public static RegisterDTO createCustomer()
    {
        return new RegisterDTO("nuricanozturk",
                "Nuri",
                "Can",
                "OZTURK",
                "canozturk309@gmail.com",
                "pass123");
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
                .withDepartureDate(LocalDate.of(2024, 2, 5))
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

    public static FlightResponseDTO provideOneWayFlightResponse(Airport from, Airport to)
    {
        var oneWayFlight = provideOneWayFlight(from, to);

        var flightInfoDTO = new FlightInfoDTO(oneWayFlight.getDepartureAirport().getCity(),
                oneWayFlight.getArrivalAirport().getCity(), oneWayFlight.getDepartureDate(), oneWayFlight.getDepartureTime(),
                oneWayFlight.getReturnDate(), oneWayFlight.getReturnTime(), null, oneWayFlight.getPrice());

        return new FlightResponseDTO(flightInfoDTO, Optional.empty());
    }


    public static FlightResponseDTO provideRoundTrapFlightResponse(Airport from, Airport to)
    {
        var roundTrapFlight = provideRoundTrapFlight(from, to);

        var departureFlightInfoDTO = new FlightInfoDTO(roundTrapFlight.getDepartureAirport().getCity(),
                roundTrapFlight.getArrivalAirport().getCity(), roundTrapFlight.getDepartureDate(), roundTrapFlight.getDepartureTime(),
                roundTrapFlight.getReturnDate(), roundTrapFlight.getReturnTime(), null, roundTrapFlight.getPrice());

        var returnFlight = roundTrapFlight.getReturnFlight();

        var returnFlightInfoDTO = new FlightInfoDTO(returnFlight.getDepartureAirport().getCity(),
                returnFlight.getArrivalAirport().getCity(), returnFlight.getDepartureDate(), returnFlight.getDepartureTime(),
                returnFlight.getReturnDate(), returnFlight.getReturnTime(), null, returnFlight.getPrice());

        return new FlightResponseDTO(departureFlightInfoDTO, of(returnFlightInfoDTO));
    }
}
