package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.service.search.flight.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "flight-provider-service", url = "http://localhost:3435/api/generator/flight")
public interface IFlightProviderService
{
    @GetMapping("/generate")
    List<FlightDTO> generateFlights();
}
