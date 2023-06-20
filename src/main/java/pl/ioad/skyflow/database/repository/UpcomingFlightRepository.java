package pl.ioad.skyflow.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.UpcomingFlight;

import java.util.Date;
import java.util.Optional;

public interface UpcomingFlightRepository extends JpaRepository<UpcomingFlight, Long> {

    Optional<UpcomingFlight> findByIcao24AndDepartureAirportAndDepartureDate(String icao24, String departureAirport, Date departureDate);

}
