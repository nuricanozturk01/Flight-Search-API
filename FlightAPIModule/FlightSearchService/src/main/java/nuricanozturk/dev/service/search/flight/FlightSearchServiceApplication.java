package nuricanozturk.dev.service.search.flight;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import nuricanozturk.dev.data.flight.entity.Customer;
import nuricanozturk.dev.data.flight.entity.Role;
import nuricanozturk.dev.data.flight.repository.ICustomerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static nuricanozturk.dev.data.flight.util.RepositoryBeanName.AIRPORT_REPOSITORY_BEAN_NAME;
import static nuricanozturk.dev.service.search.flight.util.FlightServiceBeanName.FLIGHT_SERVICE_BEAN_NAME;

/**
 * FlightSearchServiceApplication is the main entry point for the flight search service application.
 * It configures Spring Boot application settings, security, repositories, and entity scanning.
 * It also implements ApplicationRunner to perform any actions on application startup.
 * <p>
 * Some classes in callofproject package are inspired from C and System Programmers Association's Java Course.
 */
@SpringBootApplication
@ComponentScan(basePackages = {AIRPORT_REPOSITORY_BEAN_NAME, FLIGHT_SERVICE_BEAN_NAME})
@EnableJpaRepositories(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
@EntityScan(basePackages = AIRPORT_REPOSITORY_BEAN_NAME)
@EnableFeignClients
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class FlightSearchServiceApplication implements ApplicationRunner
{
    private final ICustomerRepository m_customerRepository;
    private final PasswordEncoder m_passwordEncoder;

    /**
     * Constructs the FlightSearchServiceApplication with necessary dependencies.
     *
     * @param customerRepository The repository for customer data access.
     * @param passwordEncoder    The encoder for password encryption.
     */
    public FlightSearchServiceApplication(ICustomerRepository customerRepository, PasswordEncoder passwordEncoder)
    {
        m_customerRepository = customerRepository;
        m_passwordEncoder = passwordEncoder;
    }

    /**
     * The main method to start the Spring Boot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(FlightSearchServiceApplication.class, args);
    }

    /**
     * Runs specific actions on application startup.
     * This method checks for the existence of an admin user and creates one if not present.
     *
     * @param args Application arguments.
     * @throws Exception if an error occurs during startup actions.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        if (m_customerRepository.findByUsername("admin").isEmpty())
        {
            var admin = new Customer("admin", m_passwordEncoder.encode("admin_pass123"), "Admin", "Admin", "Admin",
                    "nuricanozturk01@gmail.com", Role.ROLE_ADMIN);
            m_customerRepository.save(admin);
        }
        System.out.println("Admin username: " + "admin" + " Admin Password:" + "admin_pass123");
    }
}
