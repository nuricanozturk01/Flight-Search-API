package nuricanozturk.dev.service.search.flight.service.impl;

import nuricanozturk.dev.service.search.flight.service.IAdminService;
import nuricanozturk.dev.service.search.flight.service.IFlightProviderService;
import nuricanozturk.dev.service.search.flight.service.ISchedulerService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;

/**
 * SchedulerService handles scheduled tasks in the flight management system.
 * This service is responsible for periodically invoking flight search and update operations.
 * It utilizes the services of IAdminService and IFlightProviderService to manage flight data.
 */
@EnableScheduling
@Service
public class SchedulerService implements ISchedulerService
{
    private final IAdminService m_adminService;
    private final IFlightProviderService m_flightProviderService;

    /**
     * Constructs a SchedulerService with the necessary dependencies.
     *
     * @param adminService          The administrative service used for managing flight data.
     * @param flightProviderService The service used for providing flight data.
     */
    public SchedulerService(IAdminService adminService, IFlightProviderService flightProviderService)
    {
        m_adminService = adminService;
        m_flightProviderService = flightProviderService;
    }

    /**
     * Schedules the flight search operation to run at specific times.
     * This method is scheduled to run daily at 03:00 AM and 15:00 PM Istanbul time.
     * It retrieves generated flights from the flight provider service and saves them using the admin service.
     */
    @Schedules({
            @Scheduled(cron = "00 00 03 * * *", zone = "Europe/Istanbul"),
            @Scheduled(cron = "00 00 15 * * *", zone = "Europe/Istanbul")
    })
    @Override
    public void scheduleFlightSearch()
    {
        doForDataService(() -> m_adminService.saveAllFlights(m_flightProviderService.generateFlights()),
                "SchedulerService::scheduleFlightSearch");
    }
}