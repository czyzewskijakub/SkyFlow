package pl.ioad.skyflow.logic.cart;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Cart;
import pl.ioad.skyflow.database.model.Ticket;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.CartRepository;
import pl.ioad.skyflow.database.repository.TicketRepository;
import pl.ioad.skyflow.logic.cart.dto.CartDTO;
import pl.ioad.skyflow.logic.cart.dto.CartMapper;
import pl.ioad.skyflow.logic.cart.payload.request.CartRequest;
import pl.ioad.skyflow.logic.cart.payload.response.AddToCartResponse;
import pl.ioad.skyflow.logic.cart.payload.response.CheckoutResponse;
import pl.ioad.skyflow.logic.cart.payload.response.RemoveFromCartResponse;
import pl.ioad.skyflow.logic.exception.type.DuplicatedDataException;
import pl.ioad.skyflow.logic.exception.type.ForbiddenException;
import pl.ioad.skyflow.logic.ticket.TicketService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    private final TicketService ticketService;

    private final CartMapper cartMapper;
    private final TicketRepository ticketRepository;

    public AddToCartResponse addToCart(CartRequest request, HttpServletRequest httpServletRequest) {
        Optional<Ticket> ticket = ticketRepository.findById(request.ticketId());
        if (ticket.isEmpty()) {
            throw new EntityNotFoundException("Ticket not found");
        }
        if (ticket.get().getUser().equals(ticketService.extractUser(httpServletRequest))) {
            Optional<Cart> cart = cartRepository.findByTicket(ticket.get());
            if (cart.isEmpty()) {
                cartRepository.save(
                        Cart.builder()
                                .user(ticket.get().getUser())
                                .ticket(ticket.get())
                                .additionDate(new Date())
                                .build()

                );
                return new AddToCartResponse("Successfully added to cart");
            }
            throw new DuplicatedDataException("Ticket already in cart");
        }
        throw new ForbiddenException("Forbidden to add ticket");
    }

    public RemoveFromCartResponse removeFromCart(Long ticketId, HttpServletRequest httpServletRequest) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isEmpty()) {
            throw new EntityNotFoundException("Ticket not found");
        }
        if (ticket.get().getUser().equals(ticketService.extractUser(httpServletRequest))) {
            Optional<Cart> cart = cartRepository.findByTicket(ticket.get());
            if (cart.isEmpty()) {
                throw new EntityNotFoundException("Ticket not found in cart");
            }
            cartRepository.delete(cart.get());
            ticketRepository.delete(ticket.get());
            return new RemoveFromCartResponse("Successfully removed ticket");
        }
        throw new ForbiddenException("Forbidden to delete ticket");
    }

    public List<CartDTO> getCartItems(HttpServletRequest httpServletRequest) {
        return cartRepository.findAll().stream().filter(ticket -> ticket.getUser() == ticketService.extractUser(httpServletRequest))
                .map(cartMapper::map).toList();
    }

    public CheckoutResponse calculateTotalPrice(HttpServletRequest httpServletRequest) {
        double totalPrice = cartRepository.findAll().stream().filter(ticket ->
                        ticket.getUser() == ticketService.extractUser(httpServletRequest))
                .mapToDouble(cart -> cart.getTicket().getPrice()).sum();
        if (totalPrice == 0) {
            throw new EntityNotFoundException("Total price is equal to 0.");
        }
        return new CheckoutResponse(totalPrice);

    }


}
