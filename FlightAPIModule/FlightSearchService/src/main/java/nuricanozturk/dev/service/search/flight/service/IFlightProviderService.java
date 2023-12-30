package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.data.common.dto.FlightDTO;
import nuricanozturk.dev.data.common.util.pair.Pair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "${flight.provider.service.name}", url = "${flight.provider.url}")
public interface IFlightProviderService
{
    @GetMapping("/generate/round/${flight.provider.localization}")
    List<Pair<FlightDTO, FlightDTO>> generateRoundTripFlights();

    @GetMapping("/generate/one-way/${flight.provider.localization}")
    List<FlightDTO> generateOneWayFlights();
}
