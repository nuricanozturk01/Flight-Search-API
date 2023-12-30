package nuricanozturk.dev.service.search.flight.service;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.service.search.flight.dto.FlightDTO;
import nuricanozturk.dev.service.search.flight.dto.ResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.request.CreateAirportDTO;
import nuricanozturk.dev.service.search.flight.dto.request.CreateFlightDTO;
import nuricanozturk.dev.service.search.flight.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.service.search.flight.dto.request.UpdateFlightDTO;

import java.util.List;
import java.util.UUID;

/**
 * IAdminService interface provides administrative functionalities for managing flights and airports.
 * This includes creating, updating, and deleting flights and airports, as well as retrieving information about them.
 */
public interface IAdminService
{
    /**
     * Creates a new flight based on the provided CreateFlightDTO.
     *
     * @param createFlightDTO the DTO containing the details of the flight to be created
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if there's an issue during the flight creation process
     */
    ResponseDTO createFlight(CreateFlightDTO createFlightDTO);

    /**
     * Creates a new airport based on the provided CreateAirportDTO.
     *
     * @param createAirportDTO the DTO containing the details of the airport to be created
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if there's an issue during the airport creation process
     */
    ResponseDTO createAirport(CreateAirportDTO createAirportDTO);

    /**
     * Updates an existing flight based on the provided UpdateFlightDTO.
     *
     * @param updateFlightDTO the DTO containing the updated details of the flight
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if the specified flight is not found or if there's an issue during the update process
     */
    ResponseDTO updateFlight(UpdateFlightDTO updateFlightDTO);

    /**
     * Updates an existing airport with the provided details.
     *
     * @param updateAirportDTO Data transfer object containing the updated airport details.
     * @return ResponseDTO indicating the success or failure of the operation along with the updated airport data.
     * @throws DataServiceException if the specified airport is not found or if there's an issue during the update process.
     */
    ResponseDTO updateAirport(UpdateAirportDTO updateAirportDTO);

    /**
     * Deletes a flight identified by the given flight ID.
     *
     * @param flightId Unique identifier of the flight to be deleted.
     * @return ResponseDTO indicating the success of the operation including a message about the removed flight.
     * @throws DataServiceException if the specified flight is not found or if there's an issue during the deletion process.
     */
    ResponseDTO deleteFlightById(UUID flightId);

    /**
     * Deletes an airport identified by the given airport ID.
     *
     * @param airportId Unique identifier of the airport to be deleted.
     * @return ResponseDTO indicating the success of the operation including a message about the removed airport.
     * @throws DataServiceException if the specified airport is not found or if there's an issue during the deletion process.
     */
    ResponseDTO deleteAirportById(UUID airportId);

    /**
     * Retrieves a paginated list of all flights.
     *
     * @param page The page number for pagination.
     * @return ResponseDTO containing the list of flights for the specified page along with pagination details.
     */
    ResponseDTO findAllFlights(int page);

    /**
     * Retrieves a paginated list of all airports.
     *
     * @param page The page number for pagination.
     * @return ResponseDTO containing the list of airports for the specified page along with pagination details.
     */
    ResponseDTO findAllAirports(int page);

    /**
     * Creates a new flight based on the provided CreateFlightDTO.
     *
     * @param flights the DTO containing the details of the flight to be created
     * @throws DataServiceException if there's an issue during the flight creation process
     */
    void saveAllFlights(List<FlightDTO> flights);
}
