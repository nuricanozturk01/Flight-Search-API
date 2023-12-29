package nuricanozturk.dev.data.flight.repository;

import nuricanozturk.dev.data.flight.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, UUID>
{
    Optional<Customer> findByUsername(String username);
}
