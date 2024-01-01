package nuricanozturk.dev.service.search.flight.service;

/**
 * ISchedulerService interface handles scheduled tasks in the flight management system.
 * This service is responsible for periodically invoking flight search and update operations.
 * It utilizes the services of IAdminService and IFlightProviderService to manage flight data.
 */
public interface ISchedulerService
{
    /**
     * Schedules the flight search operation to run at specific times.
     * This method is scheduled to run daily at 03:00 AM Istanbul time.
     * It retrieves generated flights from the flight provider service and saves them using the admin service.
     */
    void scheduleFlightSearch();
}
