package nuricanozturk.dev.service.search.flight.dto;

import java.util.ArrayList;
import java.util.List;

public class FlightsResponseDTO
{
    private final List<FlightResponseDTO> flights;

    public FlightsResponseDTO()
    {
        this.flights = new ArrayList<>();
    }

    public FlightsResponseDTO(List<FlightResponseDTO> flights)
    {
        this.flights = flights;
    }

    public List<FlightResponseDTO> getFlights()
    {
        return flights;
    }
}
