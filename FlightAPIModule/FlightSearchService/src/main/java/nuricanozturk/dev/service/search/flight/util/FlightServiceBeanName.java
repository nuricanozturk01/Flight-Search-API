package nuricanozturk.dev.service.search.flight.util;

/**
 * FlightServiceBeanName is a utility class containing constants used for flight service configuration.
 * It includes bean names and property file references used within the flight service module.
 * This class is not meant to be instantiated.
 */
public final class FlightServiceBeanName
{
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private FlightServiceBeanName()
    {
    }

    /**
     * Constant for the flight service bean name.
     */
    public static final String FLIGHT_SERVICE_BEAN_NAME = "nuricanozturk.dev.service.search.flight";
    /**
     * Constant for the test properties file path.
     */
    public static final String TEST_PROPERTIES_FILE = "classpath:application-test.properties";
}
