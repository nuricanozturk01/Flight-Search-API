package nuricanozturk.dev.service.search.flight.service.impl;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.dto.FlightDTO;
import nuricanozturk.dev.service.search.flight.dto.ResponseDTO;
import nuricanozturk.dev.service.search.flight.dto.request.CreateAirportDTO;
import nuricanozturk.dev.service.search.flight.dto.request.CreateFlightDTO;
import nuricanozturk.dev.service.search.flight.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.service.search.flight.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.service.search.flight.mapper.IFlightMapper;
import nuricanozturk.dev.service.search.flight.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;

/**
 * AdminService provides administrative functionalities for managing flights and airports.
 * This includes creating, updating, and deleting flights and airports, as well as retrieving information about them.
 */
@Service
public class AdminService implements IAdminService
{
    private final FlightServiceHelper m_flightServiceHelper;
    private final IFlightMapper m_flightMapper;

    /**
     * Constructs an AdminService with the specified FlightServiceHelper and IFlightMapper.
     *
     * @param flightServiceHelper the helper service for flight operations
     * @param flightMapper        the mapper for converting flight DTOs to entities
     */
    public AdminService(FlightServiceHelper flightServiceHelper, IFlightMapper flightMapper)
    {
        m_flightServiceHelper = flightServiceHelper;
        m_flightMapper = flightMapper;
    }

    /**
     * Creates a new flight based on the provided CreateFlightDTO.
     *
     * @param createFlightDTO the DTO containing the details of the flight to be created
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if there's an issue during the flight creation process
     */
    @Override
    public ResponseDTO createFlight(CreateFlightDTO createFlightDTO)
    {
        var departureAirport = m_flightServiceHelper.saveAirport(createFlightDTO.departureAirport());
        var arrivalAirport = m_flightServiceHelper.saveAirport(createFlightDTO.arrivalAirport());

        var flight = new Flight.Builder()
                .withArrivalAirport(arrivalAirport)
                .withDepartureAirport(departureAirport)
                .withDepartureTime(createFlightDTO.departureTime())
                .withDepartureDate(createFlightDTO.departureDate())
                .withReturnDate(createFlightDTO.returnDate().orElse(null))
                .withReturnTime(createFlightDTO.returnTime().orElse(null))
                .withPrice(createFlightDTO.price())
                .build();

        var savedFlight = doForDataService(() -> m_flightServiceHelper.saveFlight(flight), "AdminService::createFlight");

        return new ResponseDTO(null, null, null, "Success", savedFlight);
    }

    /**
     * Creates a new flight based on the provided CreateFlightDTO.
     *
     * @param flights the DTO containing the details of the flight to be created
     * @throws DataServiceException if there's an issue during the flight creation process
     */
    @Override
    public void saveAllFlights(List<FlightDTO> flights)
    {
        m_flightServiceHelper.saveAllFlights(flights.stream()
                .map(this::toFlightForCreate)
                .peek(s -> System.out.println(s.getDepartureAirport()))
                .toList());
    }

    /**
     * Creates a new airport based on the provided CreateAirportDTO.
     *
     * @param createAirportDTO the DTO containing the details of the airport to be created
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if there's an issue during the airport creation process
     */
    @Override
    public ResponseDTO createAirport(CreateAirportDTO createAirportDTO)
    {
        var savedAirport = doForDataService(() -> m_flightServiceHelper.saveAirport(createAirportDTO.city()), "AdminService::createAirport");

        return new ResponseDTO(null, null, null, "Success", savedAirport);
    }

    /**
     * Updates an existing flight based on the provided UpdateFlightDTO.
     *
     * @param updateFlightDTO the DTO containing the updated details of the flight
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if the specified flight is not found or if there's an issue during the update process
     */
    @Override
    public ResponseDTO updateFlight(UpdateFlightDTO updateFlightDTO)
    {
        var currentFlight = findFlightByIdIfExists(updateFlightDTO.id());

        var departureAirport = m_flightServiceHelper.saveAirport(updateFlightDTO.departureAirport());
        var arrivalAirport = m_flightServiceHelper.saveAirport(updateFlightDTO.arrivalAirport());

        var updatedFlightDTO = m_flightMapper.toFlight(updateFlightDTO, departureAirport, arrivalAirport, currentFlight.getId());

        var updatedFlight = doForDataService(() -> m_flightServiceHelper.saveFlight(updatedFlightDTO), "AdminService::updateFlight");

        return new ResponseDTO(null, null, null, "Success", updatedFlight);
    }

    /**
     * Updates an existing airport with the provided details.
     *
     * @param updateAirportDTO Data transfer object containing the updated airport details.
     * @return ResponseDTO indicating the success or failure of the operation along with the updated airport data.
     * @throws DataServiceException if the specified airport is not found or if there's an issue during the update process.
     */
    @Override
    public ResponseDTO updateAirport(UpdateAirportDTO updateAirportDTO)
    {
        var currentAirport = findAirportByIdIfExists(updateAirportDTO.id());

        currentAirport.setCity(updateAirportDTO.city());

        var updatedAirport = doForDataService(() -> m_flightServiceHelper.saveAirport(currentAirport), "AdminService::updateAirport");

        return new ResponseDTO(null, null, null, "Success", updatedAirport);
    }

    /**
     * Deletes a flight identified by the given flight ID.
     *
     * @param flightId Unique identifier of the flight to be deleted.
     * @return ResponseDTO indicating the success of the operation including a message about the removed flight.
     * @throws DataServiceException if the specified flight is not found or if there's an issue during the deletion process.
     */
    @Override
    public ResponseDTO deleteFlightById(UUID flightId)
    {
        findFlightByIdIfExists(flightId);// if not found, throws DataServiceException

        m_flightServiceHelper.deleteFlightById(flightId);

        return new ResponseDTO(null, null, null, "Success", flightId.toString() + " removed successfully!");
    }

    /**
     * Deletes an airport identified by the given airport ID.
     *
     * @param airportId Unique identifier of the airport to be deleted.
     * @return ResponseDTO indicating the success of the operation including a message about the removed airport.
     * @throws DataServiceException if the specified airport is not found or if there's an issue during the deletion process.
     */
    @Override
    public ResponseDTO deleteAirportById(UUID airportId)
    {
        findAirportByIdIfExists(airportId);// if not found, throws DataServiceException

        m_flightServiceHelper.deleteAirportById(airportId);

        return new ResponseDTO(null, null, null, "Success", airportId.toString() + " removed successfully!");
    }

    /**
     * Retrieves a paginated list of all flights.
     *
     * @param page The page number for pagination.
     * @return ResponseDTO containing the list of flights for the specified page along with pagination details.
     */
    @Override
    public ResponseDTO findAllFlights(int page)
    {
        var pageableFlights = m_flightServiceHelper.findAllFlights(page);

        return new ResponseDTO(page, pageableFlights.getTotalPages(), pageableFlights.getContent().size(), "Success", pageableFlights.getContent());
    }

    /**
     * Retrieves a paginated list of all airports.
     *
     * @param page The page number for pagination.
     * @return ResponseDTO containing the list of airports for the specified page along with pagination details.
     */
    @Override
    public ResponseDTO findAllAirports(int page)
    {
        var pageableAirports = m_flightServiceHelper.findAllAirports(page);

        return new ResponseDTO(page, pageableAirports.getTotalPages(), pageableAirports.getContent().size(), "Success", pageableAirports.getContent());
    }

    /**
     * Retrieves a flight by its ID if it exists in the database.
     * This method checks the existence of the flight and throws an exception if the flight is not found.
     *
     * @param flightId The unique identifier of the flight to be retrieved.
     * @return The Flight object if found.
     * @throws DataServiceException if no flight is found with the given ID.
     */
    private Flight findFlightByIdIfExists(UUID flightId)
    {
        var currentFlight = m_flightServiceHelper.findFlightById(flightId);

        if (currentFlight.isEmpty())
            throw new DataServiceException("Flight Not Found!");

        return currentFlight.get();
    }

    /**
     * Retrieves an airport by its ID if it exists in the database.
     * This method checks the existence of the airport and throws an exception if the airport is not found.
     *
     * @param airportId The unique identifier of the airport to be retrieved.
     * @return The Airport object if found.
     * @throws DataServiceException if no airport is found with the given ID.
     */
    private Airport findAirportByIdIfExists(UUID airportId)
    {
        var airport = m_flightServiceHelper.findAirportById(airportId);

        if (airport.isEmpty())
            throw new DataServiceException("Airport Not Found!");

        return airport.get();
    }

    /**
     * Creates a new flight based on the provided CreateFlightDTO.
     *
     * @param flightDTO the DTO containing the details of the flight to be created
     * @return a ResponseDTO containing the result of the operation
     * @throws DataServiceException if there's an issue during the flight creation process
     */
    private Flight toFlightForCreate(FlightDTO flightDTO)
    {
        var departureAirport = m_flightServiceHelper.saveAirport(flightDTO.getDepartureAirport());
        var arrivalAirport = m_flightServiceHelper.saveAirport(flightDTO.getArrivalAirport());

        return new Flight.Builder()
                .withArrivalAirport(arrivalAirport)
                .withDepartureAirport(departureAirport)
                .withDepartureTime(flightDTO.getDepartureTime())
                .withDepartureDate(flightDTO.getDepartureDate())
                .withReturnDate(flightDTO.getReturnDate() == null ? null : flightDTO.getReturnDate())
                .withReturnTime(flightDTO.getReturnTime() == null ? null : flightDTO.getReturnTime())
                .withPrice(flightDTO.getPrice())
                .build();
    }
}
