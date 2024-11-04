package tcs.com.flights_booking_system.services;

import java.time.LocalDateTime;
import java.util.List;

import tcs.com.flights_booking_system.entities.FlightEntity;

public interface FlightService {

	FlightEntity create(FlightEntity flightEntity);

	List<FlightEntity> allView();

	FlightEntity viewEntity(Long id);

	FlightEntity update(FlightEntity flightEntity);

	void delete(Long id);

	boolean isExist(Long id);

	List<FlightEntity> searchCityOrigin (String cityOrigin);

	List<FlightEntity> searchCityDestination (String cityDestination);

	List<FlightEntity> rangePrice(Double min, Double max);

	List<FlightEntity> searchDepartureDateTime(LocalDateTime startDate, LocalDateTime endDate);
	
}
