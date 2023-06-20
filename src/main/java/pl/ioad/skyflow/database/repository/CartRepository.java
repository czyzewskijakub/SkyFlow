package pl.ioad.skyflow.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ioad.skyflow.database.model.Cart;
import pl.ioad.skyflow.database.model.Ticket;
import pl.ioad.skyflow.database.model.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

   Optional<Cart> findByTicket(Ticket ticket);

}
