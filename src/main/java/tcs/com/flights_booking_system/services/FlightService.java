package tcs.com.flights_booking_system.services;

import java.util.List;

import tcs.com.flights_booking_system.entities.FlightEntity;

public interface FlightService {

	FlightEntity create (FlightEntity flightEntity);
	
	List <FlightEntity> allView ();
	
	FlightEntity viewEntity (Long id);
	
	FlightEntity update (FlightEntity flightEntity);
	
	void delete (Long id);
	
}
