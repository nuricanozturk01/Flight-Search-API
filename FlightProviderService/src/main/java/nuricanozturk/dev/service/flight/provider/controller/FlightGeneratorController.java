package nuricanozturk.dev.service.flight.provider.controller;

import nuricanozturk.dev.data.common.util.pair.Pair;
import nuricanozturk.dev.service.flight.provider.dto.FlightDTO;
import nuricanozturk.dev.service.flight.provider.dto.Localization;
import nuricanozturk.dev.service.flight.provider.service.FlightGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/generator/flight")
public class FlightGeneratorController
{
    private final FlightGeneratorService m_flightGeneratorService;

    public FlightGeneratorController(FlightGeneratorService flightGeneratorService)
    {
        m_flightGeneratorService = flightGeneratorService;
    }

    @GetMapping("/generate/round/tr")
    public List<Pair<FlightDTO, FlightDTO>> generateRoundTripFlightsTR()
    {
        return m_flightGeneratorService.provideRoundTripFlights(Localization.TR);
    }

    @GetMapping("/generate/one-way/tr")
    public List<FlightDTO> generateOneWayFlightsTR()
    {
        return m_flightGeneratorService.provideOneWayFlights(Localization.TR);
    }


    @GetMapping("/generate/round/random")
    public List<Pair<FlightDTO, FlightDTO>> generateRoundTripFlightsRandom()
    {
        return m_flightGeneratorService.provideRoundTripFlights(Localization.RANDOM);
    }

    @GetMapping("/generate/one-way/random")
    public List<FlightDTO> generateOneWayFlightsRandom()
    {
        return m_flightGeneratorService.provideOneWayFlights(Localization.RANDOM);
    }

}
