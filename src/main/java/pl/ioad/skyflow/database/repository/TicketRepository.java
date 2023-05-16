package pl.ioad.skyflow.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
