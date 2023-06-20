package pl.ioad.skyflow.logic.ticket;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Cart;
import pl.ioad.skyflow.database.model.Ticket;
import pl.ioad.skyflow.database.model.TicketStatus;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.CartRepository;
import pl.ioad.skyflow.database.repository.TicketRepository;
import pl.ioad.skyflow.database.repository.UpcomingFlightRepository;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.cart.CartService;
import pl.ioad.skyflow.logic.exception.type.ForbiddenException;
import pl.ioad.skyflow.logic.exception.type.InvalidBusinessArgumentException;
import pl.ioad.skyflow.logic.ticket.dto.TicketMapper;
import pl.ioad.skyflow.logic.ticket.dto.TicketDTO;
import pl.ioad.skyflow.logic.ticket.payload.request.CancelRequest;
import pl.ioad.skyflow.logic.ticket.payload.request.FlightRequest;
import pl.ioad.skyflow.logic.ticket.payload.response.TicketResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CartRepository cartRepository;
    private final UpcomingFlightRepository flightRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final TicketMapper ticketMapper;

    public TicketResponse bookFlight(FlightRequest request, HttpServletRequest http) {
        User user = extractUser(http);
        var flight = flightRepository.findById(request.getFlightId());
        if (flight.isEmpty()) {
            throw new EntityNotFoundException("Give flight does not exist");
        }
        Ticket ticket = Ticket.builder()
                .flight(flight.get())
                .user(user)
                .identificationNumber(request.getIdentificationNumber())
                .status(TicketStatus.valueOf(request.getStatus()))
                .name(request.getName())
                .surname(request.getSurname())
                .travelClass(request.getTravelClass())
                .price(request.getPrice())
                .seatNumber(request.getSeatNumber())
                .build();
        ticketRepository.save(ticket);
        cartRepository.save(Cart.builder()
                .user(user)
                .ticket(ticket)
                .additionDate(new Date())
                .build());
        return new TicketResponse("Successfully booked flight");
    }

    public TicketResponse cancelFlight(CancelRequest request, HttpServletRequest http) {
        User user = extractUser(http);

        var reservation = ticketRepository.findById(request.ticketId());
        if (reservation.isEmpty())
            throw new InvalidBusinessArgumentException("This reservation does not exist");
        if (!reservation.get().getUser().getUserId().equals(user.getUserId()))
            throw new InvalidBusinessArgumentException("You were not booked for this flight");

        ticketRepository.delete(reservation.get());

        return new TicketResponse("Successfully canceled flight");
    }

    public List<TicketDTO> retrieveTickets(HttpServletRequest http) {
        User user = extractUser(http);
        return ticketRepository.findAll().stream()
                .filter(e -> e.getUser().getUserId().equals(user.getUserId()))
                .map(ticketMapper::map)
                .toList();
    }

    public User extractUser(HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null)
            throw new ForbiddenException("You are not authorized");
        token = token.substring("Bearer ".length());

        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isEmpty())
            throw new ForbiddenException("You need to be logged in");
        return user.get();
    }

}
