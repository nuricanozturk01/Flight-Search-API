package nuricanozturk.dev.service.flight.provider.service;

import net.datafaker.Faker;
import nuricanozturk.dev.data.common.util.pair.Pair;
import nuricanozturk.dev.service.flight.provider.dto.FlightDTO;
import nuricanozturk.dev.service.flight.provider.dto.Localization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.stream.IntStream.generate;
import static java.util.stream.Stream.generate;
import static nuricanozturk.dev.service.flight.provider.util.CityProvider.getRandomTurkishCity;

@Configuration
public class FlightGeneratorService
{
    private final Faker m_faker;
    private final Random m_random;

    @Value("${flight.provider.min}")
    private int m_min;

    @Value("${flight.provider.minPrice}")
    private double m_minPrice;

    @Value("${flight.provider.max}")
    private int m_max;

    @Value("${flight.provider.maxPrice}")
    private double m_maxPrice;

    @Value("${flight.provider.date-range:20}")
    private int m_dateRange;
    @Value("${flight.provider.return-date-range:20}")
    private int m_returnDateRange;

    public FlightGeneratorService(Faker faker, Random random)
    {
        m_faker = faker;
        m_random = random;
    }

    private Function<Random, String> getRandomCity(Localization localization)
    {
        return switch (localization)
        {
            case TR -> str -> getRandomTurkishCity(m_random);
            case RANDOM -> str -> m_faker.address().city();
        };
    }


    public Pair<FlightDTO, FlightDTO> generateRoundTripFlight(Function<Random, String> randomCity)
    {
        var departureDate = generateDepartureDateTime();
        var returnDate = generateReturnDateTime(departureDate);
        var departureCity = randomCity.apply(m_random);
        var returnCity = generateCity(departureCity, randomCity);
        var price = m_faker.number().numberBetween((int) m_minPrice, (int) m_maxPrice);
        var time = generateRandomLocalTime();
        var returnTime = generateRandomLocalTime();
        if (returnCity.isEmpty())
            generateRoundTripFlight(randomCity);

        var departureFlight = new FlightDTO();
        departureFlight.setPrice(price);
        departureFlight.setDepartureAirport(departureCity);
        departureFlight.setArrivalAirport(returnCity.get());
        departureFlight.setDepartureDate(departureDate);
        departureFlight.setDepartureTime(time);
        departureFlight.setReturnDate(returnDate);
        departureFlight.setReturnTime(returnTime);

        var returnFlight = new FlightDTO();
        returnFlight.setPrice(price);
        returnFlight.setDepartureAirport(returnCity.get());
        returnFlight.setArrivalAirport(departureCity);
        returnFlight.setDepartureDate(returnDate);
        returnFlight.setDepartureTime(returnTime);
        returnFlight.setReturnDate(null);
        returnFlight.setReturnTime(null);

        departureFlight.setReturnFlight(returnFlight);

        return Pair.of(departureFlight, returnFlight);
    }

    public List<FlightDTO> provideOneWayFlights(Localization localization)
    {
        return generate(() -> generateFlightOneWay(getRandomCity(localization)))
                .limit(m_random.nextInt(m_min, m_max))
                .toList();
    }

    public List<Pair<FlightDTO, FlightDTO>> provideRoundTripFlights(Localization localization)
    {
        return generate(() -> generateRoundTripFlight(getRandomCity(localization)))
                .limit(m_random.nextInt(m_min, m_max))
                .toList();
    }


    private FlightDTO generateFlightOneWay(Function<Random, String> randomCity)
    {
        var departureDate = generateDepartureDateTime();
        var departureCity = randomCity.apply(m_random);
        var arrivalCity = generateCity(departureCity, randomCity);

        var flight = new FlightDTO();
        flight.setPrice(m_faker.number().numberBetween((int) m_minPrice, (int) m_maxPrice));
        flight.setDepartureAirport(departureCity);
        flight.setArrivalAirport(arrivalCity.get());
        flight.setDepartureDate(departureDate);
        flight.setDepartureTime(generateRandomLocalTime());
        flight.setReturnDate(null);
        flight.setReturnTime(null);
        flight.setReturnFlight(null);

        return flight;
    }

    private LocalTime generateRandomLocalTime()
    {
        return LocalTime.of(m_random.nextInt(24), 10 * m_random.nextInt(5), 0);
    }


    private LocalDate generateDepartureDateTime()
    {
        return m_faker.date().future(m_dateRange, TimeUnit.DAYS).toLocalDateTime().toLocalDate();
    }

    private LocalDate generateReturnDateTime(LocalDate departureDateTime)
    {
        return departureDateTime.plusDays(m_faker.number().numberBetween(1, m_returnDateRange));
    }

    private Optional<String> generateCity(String city, Function<Random, String> randomCity)
    {
        return generate(() -> 0)
                .mapToObj(i -> randomCity.apply(m_random))
                .filter(c -> !c.equals(city))
                .findFirst();
    }

}
