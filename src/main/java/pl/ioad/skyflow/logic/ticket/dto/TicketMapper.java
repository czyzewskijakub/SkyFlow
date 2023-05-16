package pl.ioad.skyflow.logic.ticket.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Ticket;

@Service
public class TicketMapper {
    public TicketDTO map(Ticket ticket) {
        return new TicketDTO
                .TicketDTOBuilder()
                .price(ticket.getPrice())
               // .id(ticket.getFlight().getFlightId())
                .availability(ticket.getAvailability())
                .seat(ticket.getSeat())
                .build();
    }
}
