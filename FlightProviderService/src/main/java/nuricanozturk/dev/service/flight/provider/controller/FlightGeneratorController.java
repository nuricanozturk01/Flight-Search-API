package nuricanozturk.dev.service.flight.provider.controller;

import nuricanozturk.dev.service.flight.provider.dto.FlightDTO;
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

    @GetMapping("/generate")
    public List<FlightDTO> generateFlights()
    {
        return m_flightGeneratorService.provideFlights();
    }
}
