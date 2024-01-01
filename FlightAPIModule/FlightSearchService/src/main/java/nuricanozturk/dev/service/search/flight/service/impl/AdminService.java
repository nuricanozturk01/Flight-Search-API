package nuricanozturk.dev.service.search.flight.service.impl;

import callofproject.dev.library.exception.service.DataServiceException;
import nuricanozturk.dev.data.common.dto.FlightDTO;
import nuricanozturk.dev.data.common.dto.FlightResponseDTO;
import nuricanozturk.dev.data.common.dto.ResponseDTO;
import nuricanozturk.dev.data.common.dto.request.CreateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.CreateFlightDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.data.common.util.pair.Pair;
import nuricanozturk.dev.data.flight.dal.FlightServiceHelper;
import nuricanozturk.dev.data.flight.entity.Airport;
import nuricanozturk.dev.data.flight.entity.Flight;
import nuricanozturk.dev.service.search.flight.mapper.IFlightMapper;
import nuricanozturk.dev.service.search.flight.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static callofproject.dev.library.exception.util.CopDataUtil.doForDataService;
import static java.util.Optional.of;
import static nuricanozturk.dev.service.search.flight.util.StringNormalization.convert;

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
        checkCreateFlightDTO(createFlightDTO);
        var departureAirport = m_flightServiceHelper.saveAirport(convert(createFlightDTO.departureAirport()));
        var arrivalAirport = m_flightServiceHelper.saveAirport(convert(createFlightDTO.arrivalAirport()));
        var returnFlight = createReturnFlight(createFlightDTO.returnFlight());

        if (returnFlight.isPresent())
            returnFlight = of(m_flightServiceHelper.saveFlight(returnFlight.get()));

        var flight = new Flight.Builder()
                .withArrivalAirport(arrivalAirport)
                .withDepartureAirport(departureAirport)
                .withDepartureTime(createFlightDTO.departureTime())
                .withDepartureDate(createFlightDTO.departureDate())
                .withReturnDate(createFlightDTO.returnDate().orElse(null))
                .withReturnTime(createFlightDTO.returnTime().orElse(null))
                .withPrice(createFlightDTO.price())
                .withReturnFlight(returnFlight.orElse(null))
                .build();

        var savedFlight = doForDataService(() -> m_flightServiceHelper.saveFlight(flight), "AdminService::createFlight");


        if (returnFlight.isEmpty())
            return new ResponseDTO(null, null, null, "Success", m_flightMapper.toFlightInfoDTO(savedFlight));
        else
        {
            var returnFlightDTO = m_flightMapper.toFlightInfoDTO(returnFlight.get());
            var departureFlightInfoDTO = m_flightMapper.toFlightInfoDTO(savedFlight);
            return new ResponseDTO(null, null, null, "Success", new FlightResponseDTO(departureFlightInfoDTO, of(returnFlightDTO)));
        }
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
        m_flightServiceHelper.saveAllFlights(flights.stream().map(this::toFlightForCreate).toList());
    }

    /**
     * Creates a new flight based on the provided CreateFlightDTO.
     *
     * @param roundTripFlights the DTO containing the details of the flight to be created
     * @throws DataServiceException if there's an issue during the flight creation process
     */
    @Override
    public void saveAllFlightsPair(List<Pair<FlightDTO, FlightDTO>> roundTripFlights)
    {
        for (var flight : roundTripFlights)
        {
            var departureFlight = flight.getFirst();
            var returnFlight = flight.getSecond();

            var savedReturnFlight = toFlightForCreate(returnFlight);
            m_flightServiceHelper.saveFlight(savedReturnFlight);

            var savedDepartureFlight = toFlightForCreate(departureFlight);
            savedDepartureFlight.setReturnFlight(savedReturnFlight);

            m_flightServiceHelper.saveFlight(savedDepartureFlight);
        }
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
        if (createAirportDTO.city() == null)
            throw new DataServiceException("City is required!");
        var savedAirport = doForDataService(() -> m_flightServiceHelper.saveAirport(convert(createAirportDTO.city())), "AdminService::createAirport");

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
        checkUpdateFlightDTO(updateFlightDTO);
        var currentFlight = findFlightByIdIfExists(updateFlightDTO.id());

        var departureAirport = m_flightServiceHelper.saveAirport(convert(updateFlightDTO.departureAirport()));
        var arrivalAirport = m_flightServiceHelper.saveAirport(convert(updateFlightDTO.arrivalAirport()));

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
        if (updateAirportDTO.city() == null)
            throw new DataServiceException("City is required!");

        var currentAirport = findAirportByIdIfExists(updateAirportDTO.id());

        currentAirport.setCity(convert(updateAirportDTO.city()));

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
     * @param city Unique identifier of the airport to be deleted.
     * @return ResponseDTO indicating the success of the operation including a message about the removed airport.
     * @throws DataServiceException if the specified airport is not found or if there's an issue during the deletion process.
     */
    @Override
    public ResponseDTO deleteAirportByCityName(String city)
    {
        var airport = m_flightServiceHelper.findAirportByCity(city);

        if (airport.isEmpty())
            throw new DataServiceException("Airport Not Found!");

        m_flightServiceHelper.deleteAirport(airport.get());

        return new ResponseDTO(null, null, null, "Success", airport.get().getId().toString() + " removed successfully!");
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
        var departureAirport = m_flightServiceHelper.saveAirport(convert(flightDTO.getDepartureAirport()));
        var arrivalAirport = m_flightServiceHelper.saveAirport(convert(flightDTO.getArrivalAirport()));

        return new Flight.Builder()
                .withArrivalAirport(arrivalAirport)
                .withDepartureAirport(departureAirport)
                .withDepartureTime(flightDTO.getDepartureTime())
                .withDepartureDate(flightDTO.getDepartureDate())
                .withReturnDate(flightDTO.getReturnDate() == null ? null : flightDTO.getReturnDate())
                .withReturnTime(flightDTO.getReturnTime() == null ? null : flightDTO.getReturnTime())
                .withReturnFlight(flightDTO.getReturnFlight() == null ? null : toFlightForCreate(flightDTO.getReturnFlight()))
                .withPrice(flightDTO.getPrice())
                .build();
    }

    /**
     * Validates the CreateFlightDTO object to ensure all required fields are present and valid.
     *
     * @param createFlightDTO The CreateFlightDTO object to validate.
     * @throws DataServiceException If any required field is missing or invalid.
     */

    private void checkCreateFlightDTO(CreateFlightDTO createFlightDTO)
    {
        if (createFlightDTO.departureAirport() == null)
            throw new DataServiceException("Departure Airport is required!");
        if (createFlightDTO.arrivalAirport() == null)
            throw new DataServiceException("Arrival Airport is required!");
        if (createFlightDTO.departureDate() == null)
            throw new DataServiceException("Departure Date is required!");
        if (createFlightDTO.departureTime() == null)
            throw new DataServiceException("Departure Time is required!");
        if (createFlightDTO.price() <= 0)
            throw new DataServiceException("Price is must be greater thane 0!");
    }

    /**
     * Creates a return flight based on the provided CreateFlightDTO, if available.
     *
     * @param createFlightDTO Optional DTO containing the return flight details.
     * @return An Optional containing the created return Flight, or empty if the DTO is not present.
     */
    private Optional<Flight> createReturnFlight(Optional<CreateFlightDTO> createFlightDTO)
    {
        if (createFlightDTO.isEmpty())
            return Optional.empty();
        var departureAirport = m_flightServiceHelper.saveAirport(convert(createFlightDTO.get().departureAirport()));
        var arrivalAirport = m_flightServiceHelper.saveAirport(convert(createFlightDTO.get().arrivalAirport()));

        var flight = new Flight.Builder()
                .withArrivalAirport(arrivalAirport)
                .withDepartureAirport(departureAirport)
                .withDepartureTime(createFlightDTO.get().departureTime())
                .withDepartureDate(createFlightDTO.get().departureDate())
                .withReturnDate(createFlightDTO.get().returnDate().orElse(null))
                .withReturnTime(createFlightDTO.get().returnTime().orElse(null))
                .withPrice(createFlightDTO.get().price())
                .build();

        return of(flight);
    }

    /**
     * Validates the UpdateFlightDTO object to ensure all required fields are present and valid.
     *
     * @param updateFlightDTO The UpdateFlightDTO object to validate.
     * @throws DataServiceException If any required field is missing or invalid.
     */
    private void checkUpdateFlightDTO(UpdateFlightDTO updateFlightDTO)
    {
        if (updateFlightDTO.departureAirport() == null)
            throw new DataServiceException("Departure Airport is required!");
        if (updateFlightDTO.arrivalAirport() == null)
            throw new DataServiceException("Arrival Airport is required!");
        if (updateFlightDTO.departureDate() == null)
            throw new DataServiceException("Departure Date is required!");
        if (updateFlightDTO.departureTime() == null)
            throw new DataServiceException("Departure Time is required!");
        if (updateFlightDTO.price() <= 0)
            throw new DataServiceException("Price is must be greater thane 0!");
    }
}
