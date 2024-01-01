package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.data.common.dto.FlightDTO;
import nuricanozturk.dev.data.common.util.pair.Pair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * IFlightProviderService is a Feign client interface for interacting with an external flight provider service.
 * This interface defines methods to retrieve generated flight data from the external service.
 */
@FeignClient(name = "${flight.provider.service.name}", url = "${flight.provider.url}")
public interface IFlightProviderService
{
    /**
     * Retrieves a list of generated round trip flights.
     * Each entry in the list is a Pair of FlightDTOs representing the outbound and return flights.
     *
     * @return A List of Pair objects, each containing a FlightDTO for the outbound and return flights.
     */
    @GetMapping("/generate/round/${flight.provider.localization}")
    List<Pair<FlightDTO, FlightDTO>> generateRoundTripFlights();

    /**
     * Retrieves a list of generated one-way flights.
     *
     * @return A List of FlightDTOs representing one-way flights.
     */
    @GetMapping("/generate/one-way/${flight.provider.localization}")
    List<FlightDTO> generateOneWayFlights();
}
