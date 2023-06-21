package pl.ioad.skyflow.database.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.Cart;
import pl.ioad.skyflow.database.model.Ticket;

public interface CartRepository extends JpaRepository<Cart, Long> {

   Optional<Cart> findByTicket(Ticket ticket);

}
