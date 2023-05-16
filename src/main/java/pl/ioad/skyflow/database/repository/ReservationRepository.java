package pl.ioad.skyflow.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
