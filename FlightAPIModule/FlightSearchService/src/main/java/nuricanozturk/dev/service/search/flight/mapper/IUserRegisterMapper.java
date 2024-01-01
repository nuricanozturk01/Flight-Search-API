package nuricanozturk.dev.service.search.flight.mapper;

import nuricanozturk.dev.data.common.dto.RegisterDTO;
import nuricanozturk.dev.data.flight.entity.Customer;
import org.mapstruct.Mapper;

/**
 * IUserRegisterMapper is an interface for MapStruct to map between RegisterDTO and Customer entity.
 * This mapper is specifically used for converting user registration data transfer objects to Customer entity objects.
 */
@Mapper(implementationName = "UserRegisterMapperImpl", componentModel = "spring")
public interface IUserRegisterMapper
{
    /**
     * Maps a RegisterDTO to a Customer entity.
     * This method is used to convert registration data provided by the user into a Customer entity for persistence.
     *
     * @param registerDTO The RegisterDTO containing user registration data.
     * @return The Customer entity mapped from the RegisterDTO.
     */
    Customer toCustomer(RegisterDTO registerDTO);
}