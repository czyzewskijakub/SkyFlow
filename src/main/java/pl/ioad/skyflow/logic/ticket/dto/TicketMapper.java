package pl.ioad.skyflow.logic.ticket.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Ticket;

@Service
public class TicketMapper {
    public TicketDto mapTicket(Ticket ticket) {
        return new TicketDto.TicketDtoBuilder()
                .ticketId(ticket.getTicketId())
                .price(ticket.getPrice())
                .travelClass(ticket.getTravelClass())
                .ticketStatus(ticket.getStatus())
                .build();
    }

}
