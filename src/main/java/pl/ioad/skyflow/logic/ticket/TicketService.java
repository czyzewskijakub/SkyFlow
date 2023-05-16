package pl.ioad.skyflow.logic.ticket;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Ticket;
import pl.ioad.skyflow.database.repository.TicketRepository;
import pl.ioad.skyflow.database.repository.UpcomingFlightRepository;
import pl.ioad.skyflow.logic.ticket.dto.TicketDTO;
import pl.ioad.skyflow.logic.ticket.dto.TicketMapper;
import pl.ioad.skyflow.logic.ticket.payload.request.TicketsListRequest;
import pl.ioad.skyflow.logic.ticket.payload.request.TicketsRequest;
import pl.ioad.skyflow.logic.ticket.payload.response.TicketResponse;
import pl.ioad.skyflow.logic.upcomingFlights.dto.UpcomingFlightsDTO;
import pl.ioad.skyflow.logic.upcomingFlights.payload.response.UpcomingFlightsResponse;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UpcomingFlightRepository upcomingFlightRepository;

    private final TicketMapper ticketMapper;

    public TicketResponse createTicket(TicketsRequest request, HttpServletRequest http) {
            Ticket ticket = Ticket.builder()
                    .price(request.getPrice())
                    .availability(request.getAvailability())
                    .seat(request.getSeat())
 //                   .flight(upcomingFlightRepository.findById(request.getId()).get())
                    .build();
//            long a = ticketRepository.findAll().stream().filter( ticket1 ->
//                    Objects.equals(ticket1.getFlight().getFlightId(), request.getId())).count();
//            if (a > upcomingFlightRepository.findById(request.getId()).get().getCapacity()) {
//                return new TicketResponse("Airplane is full");
//            } else {
                ticketRepository.save(ticket);
                return new TicketResponse("Ticket has been added");
 //           }
    }
    public List<TicketDTO> getTickets() {
        return ticketRepository.findAll().stream().map(ticketMapper::map).toList();
    }

    public TicketResponse clearTickets() {
         ticketRepository.deleteAll();
         return new TicketResponse("Tickets has been deleted");
    };
}
