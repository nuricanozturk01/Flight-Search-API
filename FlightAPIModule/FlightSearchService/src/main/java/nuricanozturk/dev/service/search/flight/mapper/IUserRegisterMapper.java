package nuricanozturk.dev.service.search.flight.mapper;

import nuricanozturk.dev.data.common.dto.RegisterDTO;
import nuricanozturk.dev.data.flight.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(implementationName = "UserRegisterMapperImpl", componentModel = "spring")
public interface IUserRegisterMapper
{
    Customer toCustomer(RegisterDTO registerDTO);
}