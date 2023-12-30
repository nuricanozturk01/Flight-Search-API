package nuricanozturk.dev.service.search.flight.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import nuricanozturk.dev.service.search.flight.dto.ErrorMessage;
import nuricanozturk.dev.service.search.flight.dto.request.CreateAirportDTO;
import nuricanozturk.dev.service.search.flight.dto.request.CreateFlightDTO;
import nuricanozturk.dev.service.search.flight.dto.request.UpdateAirportDTO;
import nuricanozturk.dev.service.search.flight.dto.request.UpdateFlightDTO;
import nuricanozturk.dev.service.search.flight.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static callofproject.dev.library.exception.util.ExceptionUtil.subscribe;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "Authorization")
public class AdminController
{
    private final AdminService m_adminService;

    public AdminController(AdminService adminService)
    {
        m_adminService = adminService;
    }

    @PostMapping("create/flight")
    public ResponseEntity<Object> createFlight(@RequestBody CreateFlightDTO createFlightDTO)
    {
        return subscribe(() -> ok(m_adminService.createFlight(createFlightDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @PostMapping("create/airport")
    public ResponseEntity<Object> upsertAirport(@RequestBody CreateAirportDTO createAirportDTO)
    {
        return subscribe(() -> ok(m_adminService.createAirport(createAirportDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @PutMapping("update/flight")
    public ResponseEntity<Object> updateFlight(@RequestBody UpdateFlightDTO updateFlightDTO)
    {
        return subscribe(() -> ok(m_adminService.updateFlight(updateFlightDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @PutMapping("update/airport")
    public ResponseEntity<Object> updateAirport(@RequestBody UpdateAirportDTO updateAirportDTO)
    {
        return subscribe(() -> ok(m_adminService.updateAirport(updateAirportDTO)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @DeleteMapping("delete/flight")
    public ResponseEntity<Object> deleteFlightById(@RequestParam("id") UUID flightId)
    {
        return subscribe(() -> ok(m_adminService.deleteFlightById(flightId)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @DeleteMapping("delete/airport")
    public ResponseEntity<Object> deleteAirport(@RequestParam("id") UUID airportId)
    {
        return subscribe(() -> ok(m_adminService.deleteAirportById(airportId)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("find/flight/all")
    public ResponseEntity<Object> findAllFlights(@RequestParam("p") int page)
    {
        return subscribe(() -> ok(m_adminService.findAllFlights(page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }

    @GetMapping("find/airport/all")
    public ResponseEntity<Object> findAllAirports(@RequestParam("p") int page)
    {
        return subscribe(() -> ok(m_adminService.findAllAirports(page)),
                ex -> badRequest().body(new ErrorMessage(false, ex.getMessage())));
    }
}
