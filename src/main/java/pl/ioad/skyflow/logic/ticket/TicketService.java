package pl.ioad.skyflow.logic.ticket;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.*;
import pl.ioad.skyflow.database.repository.CartRepository;
import pl.ioad.skyflow.database.repository.TicketRepository;
import pl.ioad.skyflow.database.repository.UpcomingFlightRepository;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.exception.type.DuplicatedDataException;
import pl.ioad.skyflow.logic.exception.type.ForbiddenException;
import pl.ioad.skyflow.logic.exception.type.InvalidBusinessArgumentException;
import pl.ioad.skyflow.logic.exception.type.InvalidDataException;
import pl.ioad.skyflow.logic.ticket.dto.TicketDTO;
import pl.ioad.skyflow.logic.ticket.dto.TicketMapper;
import pl.ioad.skyflow.logic.ticket.payload.request.FlightRequest;
import pl.ioad.skyflow.logic.ticket.payload.response.TicketResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static pl.ioad.skyflow.database.model.TicketStatus.CANCELLED;
import static pl.ioad.skyflow.database.model.TicketStatus.RESERVED;


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
        if (ticketRepository.existsBySeatNumber(request.getSeatNumber())) {
            throw new DuplicatedDataException("This seat number is already taken");
        }
        User user = extractUser(http);
        Optional<UpcomingFlight> flight = flightRepository.findById(request.getFlightId());
        if (flight.isEmpty()) {
            throw new EntityNotFoundException("Give flight does not exist");
        }

        TravelClass travelClass = TravelClass.valueOf(request.getTravelClass());

        Ticket ticket = Ticket.builder()
                .flight(flight.get())
                .user(user)
                .identificationNumber(request.getIdentificationNumber())
                .status(RESERVED)
                .name(request.getName())
                .surname(request.getSurname())
                .travelClass(travelClass)
                .price(request.getPrice() * travelClass.getWeight())
                .seatNumber(request.getSeatNumber())
                .build();
        ticketRepository.save(ticket);
        cartRepository.save(Cart.builder()
                .user(user)
                .ticket(ticket)
                .additionDate(new Date())
                .build());

        if (flight.get().getCapacity() == 0) {
            throw new InvalidBusinessArgumentException("Capacity of flight is 0");
        }

        flight.get().setCapacity(flight.get().getCapacity() - 1);
        flightRepository.save(flight.get());

        return new TicketResponse("Successfully booked flight");
    }

    public TicketResponse cancelTicket(Long ticketId, HttpServletRequest http) {
        User user = extractUser(http);

        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isEmpty()) {
            throw new InvalidBusinessArgumentException("This reservation does not exist");
        }
        if (!ticket.get().getUser().getUserId().equals(user.getUserId())) {
            throw new InvalidBusinessArgumentException("You were not booked for this flight");
        }
        if (ticket.get().getStatus().equals(CANCELLED)) {
            throw new InvalidDataException("Flight is already cancelled");
        }

        ticket.get().setStatus(CANCELLED);

        UpcomingFlight flight = ticket.get().getFlight();
        flight.setCapacity(flight.getCapacity() + 1);
        flightRepository.save(flight);

        return new TicketResponse("Successfully canceled flight");
    }

    public List<TicketDTO> retrieveTickets(HttpServletRequest http) {
        User user = extractUser(http);
        return ticketRepository.findAll().stream()
                .filter(e -> e.getUser().getUserId().equals(user.getUserId()))
                .map(ticketMapper::mapTicket)
                .toList();
    }

    public List<TravelClass> getClasses() {
        return List.of(TravelClass.values());
    }

    public TicketResponse changeTicketStatus(Long ticketId, TicketStatus status) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isEmpty()) {
            throw new EntityNotFoundException("Ticket with given ID does not exist");
        }

        ticket.get().setStatus(status);

        ticketRepository.save(ticket.get());
        return new TicketResponse("Successfully changed ticket status");
    }

    public List<TicketDTO> retrieveAllUsersTickets() {
        return ticketRepository.findAll().stream().map(ticketMapper::mapTicket).toList();
    }

    public User extractUser(HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null) {
            throw new ForbiddenException("You are not authorized");
        }
        token = token.substring("Bearer ".length());

        Optional<User> user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isEmpty()) {
            throw new ForbiddenException("You need to be logged in");
        }

        return user.get();
    }

}
