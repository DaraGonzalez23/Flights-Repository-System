package tcs.com.flights_booking_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcs.com.flights_booking_system.entities.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

}
