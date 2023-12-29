package nuricanozturk.dev.service.search.flight.service;

import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import org.springframework.stereotype.Service;

@Service
public class FlightService
{
    private final FlightServiceHelper m_flightServiceHelper;

    public FlightService(FlightServiceHelper flightServiceHelper)
    {
        m_flightServiceHelper = flightServiceHelper;
    }
}
