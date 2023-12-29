package nuricanozturk.dev.service.search.flight.mapper;

import nuricanozturk.dev.data.flight.entity.Customer;
import nuricanozturk.dev.service.search.flight.dto.RegisterDTO;
import org.mapstruct.Mapper;

@Mapper(implementationName = "UserRegisterMapperImpl", componentModel = "spring")
public interface IUserRegisterMapper
{
    Customer toCustomer(RegisterDTO registerDTO);

    RegisterDTO toRegisterDTO(Customer customer);
}