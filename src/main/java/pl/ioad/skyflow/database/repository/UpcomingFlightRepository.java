package pl.ioad.skyflow.database.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.UpcomingFlight;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UpcomingFlightRepository extends JpaRepository<UpcomingFlight, Long> {

    List<UpcomingFlight> findAllByDepartureAirportAndDepartureDateBetween(@NonNull String departureAirport,
                                                                          @NonNull Date beginDate,
                                                                          @NonNull Date endDate);
    Optional<UpcomingFlight> findByIcao24AndDepartureAirportAndDepartureDate(String icao24, String departureAirport, Date departureDate);

}
