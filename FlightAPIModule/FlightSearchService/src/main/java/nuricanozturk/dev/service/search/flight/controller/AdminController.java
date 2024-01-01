package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.data.common.dto.ErrorMessage;
import nuricanozturk.dev.data.common.dto.request.CreateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.CreateFlightDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.data.common.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.service.search.flight.service.IAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static callofproject.dev.library.exception.util.ExceptionUtil.subscribe;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * AdminController handles administrative operations for flights and airports.
 * This controller provides endpoints for creating, updating, and deleting flights and airports,
 * as well as retrieving information about them. It utilizes IAdminService for business logic.
 */
@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "Authorization")
public class AdminController
{
    private final IAdminService m_adminService;

    /**
     * Constructs an AdminController with the necessary administrative service.
     *
     * @param adminService The administrative service for handling flight and airport operations.
     */
    public AdminController(IAdminService adminService)
    {
        m_adminService = adminService;
    }

    /**
     * Creates a new flight with the provided details.
     *
     * @param createFlightDTO The data transfer object containing the flight details for creation.
     * @return A ResponseEntity containing the result of the creation operation.
     */

    @PostMapping("create/flight")

    public ResponseEntity<Object> createFlight(@RequestBody CreateFlightDTO createFlightDTO)
    {
        return subscribe(() -> ok(m_adminService.createFlight(createFlightDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Creates or updates an airport with the given details.
     *
     * @param createAirportDTO The data transfer object containing the airport details for creation or update.
     * @return A ResponseEntity containing the result of the operation.
     */
    @PostMapping("create/airport")
    public ResponseEntity<Object> createAirport(@RequestBody CreateAirportDTO createAirportDTO)
    {
        return subscribe(() -> ok(m_adminService.createAirport(createAirportDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Updates an existing flight with the provided details.
     *
     * @param updateFlightDTO The data transfer object containing the updated flight details.
     * @return A ResponseEntity containing the result of the update operation.
     */
    @PutMapping("update/flight")
    public ResponseEntity<Object> updateFlight(@RequestBody UpdateFlightDTO updateFlightDTO)
    {
        return subscribe(() -> ok(m_adminService.updateFlight(updateFlightDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Updates an existing airport with the provided details.
     *
     * @param updateAirportDTO The data transfer object containing the updated airport details.
     * @return A ResponseEntity containing the result of the update operation.
     */
    @PutMapping("update/airport")
    public ResponseEntity<Object> updateAirport(@RequestBody UpdateAirportDTO updateAirportDTO)
    {
        return subscribe(() -> ok(m_adminService.updateAirport(updateAirportDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Deletes a flight identified by the given ID.
     *
     * @param flightId The unique identifier of the flight to be deleted.
     * @return A ResponseEntity containing the result of the deletion operation.
     */
    @DeleteMapping("delete/flight")
    public ResponseEntity<Object> deleteFlightById(@RequestParam("id") UUID flightId)
    {
        return subscribe(() -> ok(m_adminService.deleteFlightById(flightId)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Deletes an airport identified by the given ID.
     *
     * @param city The unique identifier of the airport to be deleted.
     * @return A ResponseEntity containing the result of the deletion operation.
     */
    @DeleteMapping("delete/airport")
    public ResponseEntity<Object> deleteAirport(@RequestParam("city") String city)
    {
        return subscribe(() -> ok(m_adminService.deleteAirportByCityName(city)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Retrieves a paginated list of all flights.
     *
     * @param page The page number for pagination.
     * @return A ResponseEntity containing the list of flights and pagination details.
     */
    @GetMapping("find/flight/all")
    public ResponseEntity<Object> findAllFlights(@RequestParam("p") int page)
    {
        return subscribe(() -> ok(m_adminService.findAllFlights(page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    /**
     * Retrieves a paginated list of all airports.
     *
     * @param page The page number for pagination.
     * @return A ResponseEntity containing the list of airports and pagination details.
     */
    @GetMapping("find/airport/all")
    public ResponseEntity<Object> findAllAirports(@RequestParam("p") int page)
    {
        return subscribe(() -> ok(m_adminService.findAllAirports(page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }
}