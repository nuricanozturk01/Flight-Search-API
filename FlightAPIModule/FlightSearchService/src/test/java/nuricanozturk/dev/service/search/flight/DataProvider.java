package nuricanozturk.dev.service.search.flight;

import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;

public final class DataProvider
{
    private DataProvider()
    {
    }

    public static RegisterDTO createCustomer()
    {
        return new RegisterDTO("nuricanozturk",
                "Nuri",
                "Can",
                "OZTURK",
                "canozturk309@gmail.com",
                "pass123");
    }
}
