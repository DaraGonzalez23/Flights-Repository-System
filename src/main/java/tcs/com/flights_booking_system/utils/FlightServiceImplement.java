package tcs.com.flights_booking_system.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcs.com.flights_booking_system.entities.FlightEntity;
import tcs.com.flights_booking_system.repositories.FlightRepository;
import tcs.com.flights_booking_system.services.FlightService;

@Service
public class FlightServiceImplement implements FlightService {

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public FlightEntity create(FlightEntity flightEntity) {
		return flightRepository.save(flightEntity);
	}

	@Override
	public List<FlightEntity> allView() {
		return flightRepository.findAll();
	}

	@Override
	public FlightEntity viewEntity(Long id) {
//		Optional<FlightEntity> flightEntity = flightRepository.findById(id);
////		if (!flightEntity.isPresent()) {
////			return new FlightEntity();
////		} else {
//			return flightEntity.get();
//		}
		return flightRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Flight with ID " + id + " not found"));
	}

	@Override
	public FlightEntity update(FlightEntity flightEntity) {
		if (!flightRepository.existsById(flightEntity.getId())) {
			throw new IllegalArgumentException("Flight with ID " + flightEntity.getId() + " doesn´t exist.");
		}
		return flightRepository.save(flightEntity);
	}

	@Override
	public void delete(Long id) {
		if (flightRepository.existsById(id)) { // Check if the entity exists before deleting
			flightRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Flight with ID " + id + " doesn´t exist."); // the method would attempt
																							// to delete a non-existing
																							// entity
		}

	}

}
