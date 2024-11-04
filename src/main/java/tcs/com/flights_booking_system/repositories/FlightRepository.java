package tcs.com.flights_booking_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import tcs.com.flights_booking_system.entities.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
	
	// METHODS IN THE INTERFACE
		@Query("SELECT f " 
				+ " from FlightEntity f " 
				+ " where f.cityOrigin like %:searchOrigin% ")
		public List<FlightEntity> searchCityOrigin(String searchOrigin);

		@Query ("SELECT f " 
				+ " from FlightEntity f " 
				+ " where f.cityDestination like %:searchDestination% ")
		public List<FlightEntity> searchCityDestination(String searchDestination);
		
		@Query ("SELECT f "
				+ " from FlightEntity f "
				+ "where f.departureDateTime between :startDate and :endDate ")
		public List<FlightEntity> searchDepartureDateTime (LocalDateTime startDate, LocalDateTime endDate);

		
		@Query ("SELECT f "
				+ " from FlightEntity f "
				+ " where f.price between :min and :max " ) //Everytime I put a variable : and then the variable / or you can find ?, empata with the first parameter
		public List<FlightEntity> rangePrice(Double min, Double max); //It can use Search but usually use FIND


}
