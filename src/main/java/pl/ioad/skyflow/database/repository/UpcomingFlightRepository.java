package pl.ioad.skyflow.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.UpcomingFlight;

public interface UpcomingFlightRepository extends JpaRepository <UpcomingFlight, Long> {
}
