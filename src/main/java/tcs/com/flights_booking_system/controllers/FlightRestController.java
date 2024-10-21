package tcs.com.flights_booking_system.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tcs.com.flights_booking_system.entities.FlightEntity;
import tcs.com.flights_booking_system.services.FlightService;

@RestController
@RequestMapping("/api")
public class FlightRestController {

	@Autowired
	private FlightService flightService;

//@GetMapping ("/flights")
//public List<FlightEntity> viewList(){

	@GetMapping("/id/{id}")
	public ResponseEntity<Object> viewId(@PathVariable Long id) {
		return new ResponseEntity<>(flightService.viewEntity(id), HttpStatus.ACCEPTED);
	}

	@GetMapping("/flights")
	public ResponseEntity<Object> viewFlights() {
		return new ResponseEntity<>(flightService.allView(), HttpStatus.ACCEPTED);
	}

	@PostMapping("/flights")
	public ResponseEntity<Object> create(@RequestBody FlightEntity flightEntity) {
		flightEntity.setId(null); // The ID isn´t here, is generated automatically

		//Verify the name of the City Origin isn´t empty
		if (flightEntity.getCityOrigin() == null || flightEntity.getCityOrigin().trim().isEmpty()) {
            return new ResponseEntity<>("The city of origin cannot be empty", HttpStatus.BAD_REQUEST);
        }
			//Verify the name of the City Destination isn´t empty
		 if (flightEntity.getCityDestination() == null || flightEntity.getCityDestination().trim().isEmpty()) {
		        return new ResponseEntity<>("The city of destination cannot be empty", HttpStatus.BAD_REQUEST);
		    }

			//Verify the price isn´t empty
		if (flightEntity.setPrice() <= 0) {
			return new ResponseEntity<>("The price only has positive values",HttpStatus.BAD_REQUEST);
		}
		
		//System.out.println(flightEntity.getDepartureDateTime()); **To see what return and then when can see what implement for validate

		//Verify the date isn´t empty
		if (flightEntity.getDepartureTime() == null) {
			return new ResponseEntity<>("Introduce a valid date" ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(flightService.create(flightEntity),HttpStatus.CREATED); //Modify 201 status
	}

	@PutMapping("/flight/{idLong}") // Update all the object
	public ResponseEntity<Object> update(@RequestBody FlightEntity flightEntity, @PathVariable Long id) { // Access to ID from the parameter
		if (id == null) {
			return new ResponseEntity<>("Please send an Id", HttpStatus.BAD_REQUEST);
		} else {
			flightEntity.setId(id); // Update the parameters
			return new ResponseEntity<>(flightService.update(flightEntity), HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping("/flight/{idLong}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		flightService.delete(id);
		return new ResponseEntity<>("Delete succesfully", HttpStatus.ACCEPTED);
	}

}
