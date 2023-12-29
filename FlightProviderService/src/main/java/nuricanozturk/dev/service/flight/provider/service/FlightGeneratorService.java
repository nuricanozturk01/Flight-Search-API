package nuricanozturk.dev.service.flight.provider.service;

import net.datafaker.Faker;
import nuricanozturk.dev.service.flight.provider.dto.FlightDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.generate;

@Configuration
public class FlightGeneratorService
{
    private final Faker m_faker;
    private final Random m_random;

    @Value("${flight.provider.min:50}")
    private int m_min = 50;

    @Value("${flight.provider.max:10}")
    private int m_max = 100;

    private static final List<String> TURKISH_CITIES = List.of(
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya",
            "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir",
            "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur",
            "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
            "Diyarbakır", "Edirne", "Elaziğ", "Erzincan", "Erzurum",
            "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari",
            "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir",
            "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
            "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa",
            "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir",
            "Niğde", "Ordu", "Rize", "Sakarya", "Samsun",
            "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat",
            "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van",
            "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman",
            "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan",
            "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye",
            "Düzce"
    );

    public static String getRandomTurkishCity(Random random)
    {
        String turkishCity = TURKISH_CITIES.get(random.nextInt(TURKISH_CITIES.size()));
        return normalizeTurkishChars(turkishCity);
    }

    private static String normalizeTurkishChars(String turkishText)
    {
        var normalizedText = Normalizer.normalize(turkishText, Normalizer.Form.NFD);
        var pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedText)
                .replaceAll("")
                .replace('ı', 'i')
                .replace('İ', 'I')
                .replace('ğ', 'g')
                .replace('Ğ', 'G')
                .replace('ü', 'u')
                .replace('Ü', 'U')
                .replace('ş', 's')
                .replace('Ş', 'S')
                .replace('ö', 'o')
                .replace('Ö', 'O')
                .replace('ç', 'c')
                .replace('Ç', 'C')
                .toUpperCase();
    }

    public FlightGeneratorService(Faker faker, Random random)
    {
        m_faker = faker;
        m_random = random;
    }

    public List<FlightDTO> provideFlights()
    {
        var oneWayFlights = generate(this::generateFlightOneWay)
                .limit(m_random.nextInt(m_min, m_max))
                .toList();

        var roundTripFlights = generate(this::generateRoundTripFlight)
                .limit(m_random.nextInt(m_min, m_max))
                .flatMap(List::stream)
                .toList();

        return concat(oneWayFlights.stream(), roundTripFlights.stream()).toList();
    }


    private FlightDTO generateFlightOneWay()
    {
        var departureDate = generateDepartureDateTime();
        var departureCity = getRandomTurkishCity(m_random);
        var arrivalCity = generateCity(departureCity);

        FlightDTO flight = new FlightDTO();
        flight.setPrice(m_faker.number().numberBetween(1_000, 10_000));
        flight.setDepartureAirport(departureCity);
        flight.setArrivalAirport(arrivalCity.get());
        flight.setDepartureDate(departureDate);
        flight.setDepartureTime(generateRandomLocalTime());
        flight.setReturnDate(null);

        return flight;
    }

    private LocalTime generateRandomLocalTime()
    {
        int hour = m_random.nextInt(24);
        int minute = 10 * m_random.nextInt(4);

        return LocalTime.of(hour, minute, 0);
    }

    private List<FlightDTO> generateRoundTripFlight()
    {
        var departureDate = generateDepartureDateTime();
        var returnDate = generateReturnDateTime(departureDate);
        var departureCity = getRandomTurkishCity(m_random);
        var returnCity = generateCity(departureCity);
        var price = m_faker.number().numberBetween(1_000, 10_000);
        var time = generateRandomLocalTime();
        if (returnCity.isEmpty())
            generateRoundTripFlight();

        FlightDTO departureFlight = new FlightDTO();
        departureFlight.setPrice(price);
        departureFlight.setDepartureAirport(departureCity);
        departureFlight.setArrivalAirport(returnCity.get());
        departureFlight.setDepartureDate(departureDate);
        departureFlight.setDepartureTime(time);
        departureFlight.setReturnDate(returnDate);
        departureFlight.setReturnTime(generateRandomLocalTime());

        FlightDTO returnFlight = new FlightDTO();
        returnFlight.setPrice(price);
        returnFlight.setDepartureAirport(returnCity.get());
        returnFlight.setArrivalAirport(departureCity);
        returnFlight.setDepartureDate(returnDate);
        returnFlight.setDepartureTime(generateRandomLocalTime());
        returnFlight.setReturnDate(null);
        returnFlight.setReturnTime(null);

        return List.of(departureFlight, returnFlight);
    }


    private LocalDate generateDepartureDateTime()
    {
        return m_faker.date().future(10, TimeUnit.DAYS).toLocalDateTime().toLocalDate();
    }

    private LocalDate generateReturnDateTime(LocalDate departureDateTime)
    {
        return departureDateTime.plusDays(m_faker.number().numberBetween(1, 20));
    }

    private Optional<String> generateCity(String city)
    {
        return IntStream.generate(() -> 0)
                .mapToObj(i -> getRandomTurkishCity(m_random))
                .filter(c -> !c.equals(city))
                .findFirst();
    }

    public static void main(String[] args)
    {
        var faker = new Faker();
        var random = new Random();
        var flightGeneratorService = new FlightGeneratorService(faker, random);
        var flights = flightGeneratorService.provideFlights();
        System.out.println(flights);
    }

}
