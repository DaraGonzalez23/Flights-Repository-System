package tcs.com.flights_booking_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

import tcs.com.flights_booking_system.entities.FlightEntity;
import tcs.com.flights_booking_system.services.FlightService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class FlightRestController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/id/{id}/flights") // Get by ID
    public ResponseEntity<Object> viewId(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.viewEntity(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/flights") // Get all flights
    public ResponseEntity<Object> viewFlights() {
        return new ResponseEntity<>(flightService.allView(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/flights") // Create flight
    public ResponseEntity<Object> create(@RequestBody FlightEntity flightEntity) {
        flightEntity.setId(null); // ID is generated automatically

        // Verify City Origin is not empty
        if (flightEntity.getCityOrigin() == null || flightEntity.getCityOrigin().trim().isEmpty()) {
            return new ResponseEntity<>("The city of origin cannot be empty", HttpStatus.BAD_REQUEST);
        }

        // Verify City Destination is not empty
        if (flightEntity.getCityDestination() == null || flightEntity.getCityDestination().trim().isEmpty()) {
            return new ResponseEntity<>("The city of destination cannot be empty", HttpStatus.BAD_REQUEST);
        }

        // Verify the departure date is not null
        if (flightEntity.getDepartureDateTime() == null) {
            return new ResponseEntity<>("Please provide a valid departure date", HttpStatus.BAD_REQUEST);
        }

        // Verify the price is positive
        if (flightEntity.getPrice() == null || flightEntity.getPrice() <= 0) {
            return new ResponseEntity<>("The price must be a positive value", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(flightService.create(flightEntity), HttpStatus.CREATED);
    }

    @PutMapping("/flights/{id}") // Update flight
    public ResponseEntity<Object> update(@RequestBody FlightEntity flightEntity, @PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("Please provide an ID", HttpStatus.BAD_REQUEST);
        } else {
            flightEntity.setId(id); // Set ID for update
            return new ResponseEntity<>(flightService.update(flightEntity), HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/flights/{id}") // Delete flight by ID
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (flightService.isExist(id)) {
            flightService.delete(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("The ID " + id + " does not exist, please try a valid ID", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/flights/citiesOrigin/{cityOrigin}") // Get flights by city of origin
    public ResponseEntity<Object> viewCitiesOrigin(@PathVariable String cityOrigin) {
        return new ResponseEntity<>(flightService.searchCityOrigin(cityOrigin), HttpStatus.ACCEPTED);
    }

    @GetMapping("/flights/citiesDestination/{cityDestination}") // Get flights by city of destination
    public ResponseEntity<Object> viewCitiesDestination(@PathVariable String cityDestination) {
        return new ResponseEntity<>(flightService.searchCityDestination(cityDestination), HttpStatus.ACCEPTED);
    }

    @GetMapping("/flights/prices/{min}/{max}") // Get flights within a price range
    public ResponseEntity<Object> viewPrices(@PathVariable Double min, @PathVariable Double max) {
        return new ResponseEntity<>(flightService.rangePrice(min, max), HttpStatus.ACCEPTED);
    }

    @GetMapping("/flights/departuresTime/{startDate}/{endDate}") // Get flights within a date range
    public ResponseEntity<List<FlightEntity>> searchDeparturesTime(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
        List<FlightEntity> flights = flightService.searchDepartureDateTime(startDate, endDate);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
