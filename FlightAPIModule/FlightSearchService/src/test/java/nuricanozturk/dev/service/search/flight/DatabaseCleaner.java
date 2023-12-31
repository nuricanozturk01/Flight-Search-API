package nuricanozturk.dev.service.search.flight;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner
{
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void clearH2Database()
    {
        entityManager.createNativeQuery("DELETE FROM FLIGHT").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM AIRPORT").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM AUTHORITIES").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM CUSTOMER").executeUpdate();

    }
}