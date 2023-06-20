package pl.ioad.skyflow.logic.ticket.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Ticket;

@Service
public class Mapper {
    public TicketDTO map(Ticket ticket) {
        return new TicketDTO.TicketDTOBuilder().ticketId(ticket.getTicketId()).build();
    }

}
