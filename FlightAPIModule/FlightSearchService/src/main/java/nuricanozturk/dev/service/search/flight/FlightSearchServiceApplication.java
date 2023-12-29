package nuricanozturk.dev.service.search.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static nuricanozturk.dev.data.flight.util.RepositoryBeanName.AIRPORT_REPOSITORY_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.FLIGHT_SERVICE_BEAN_NAME;

@SpringBootApplication
@ComponentScan(basePackages = {AIRPORT_REPOSITORY_BEAN_NAME, FLIGHT_SERVICE_BEAN_NAME})
@EnableJpaRepositories(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
@EntityScan(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
public class FlightSearchServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(FlightSearchServiceApplication.class, args);
    }

}
