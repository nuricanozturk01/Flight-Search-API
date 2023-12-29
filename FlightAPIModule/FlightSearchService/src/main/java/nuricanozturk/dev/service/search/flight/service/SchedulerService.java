package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.data.flight.repository.IFlightRepository;
import nuricanozturk.dev.service.search.flight.dto.FlightDTO;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@EnableScheduling
@Service
public class SchedulerService
{
    private final IFlightRepository m_flightRepository;
    private final FlightServiceHelper m_flightServiceHelper;
    private final IFlightProviderService m_flightProviderService;

    public SchedulerService(IFlightRepository flightRepository, FlightServiceHelper flightServiceHelper, IFlightProviderService flightProviderService)
    {
        m_flightRepository = flightRepository;
        m_flightServiceHelper = flightServiceHelper;
        m_flightProviderService = flightProviderService;
    }


    @Schedules({
            @Scheduled(cron = "00 22 00 * * *", zone = "Europe/Istanbul"),
            @Scheduled(cron = "00 00 15 * * *", zone = "Europe/Istanbul")
    })
    public void saveFlightSchedule()
    {
        m_flightRepository.saveAll(m_flightProviderService.generateFlights().stream().map(this::toFlight).toList());
    }

    private Flight toFlight(FlightDTO flightDTO)
    {
        var departureAirport = m_flightServiceHelper.findAirportByCity(flightDTO.getDepartureAirport());
        var arrivalAirport = m_flightServiceHelper.findAirportByCity(flightDTO.getArrivalAirport());

        LocalDate returnDate = null;
        LocalTime returnTime = null;

        if (flightDTO.getReturnDate() != null)
            returnDate = flightDTO.getReturnDate();

        if (flightDTO.getReturnTime() != null)
            returnTime = flightDTO.getReturnTime();

        if (departureAirport.isEmpty())
            departureAirport = Optional.of(m_flightServiceHelper.saveAirport(flightDTO.getDepartureAirport()));

        if (arrivalAirport.isEmpty())
            arrivalAirport = Optional.of(m_flightServiceHelper.saveAirport(flightDTO.getArrivalAirport()));


        return new Flight.Builder()
                .withArrivalAirport(arrivalAirport.get())
                .withDepartureAirport(departureAirport.get())
                .withDepartureTime(flightDTO.getDepartureTime())
                .withDepartureDate(flightDTO.getDepartureDate())
                .withReturnDate(returnDate)
                .withReturnTime(returnTime)
                .withPrice(flightDTO.getPrice())
                .build();
    }
}
