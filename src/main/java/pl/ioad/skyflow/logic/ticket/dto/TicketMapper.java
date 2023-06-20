package pl.ioad.skyflow.logic.ticket.dto;

import pl.ioad.skyflow.database.model.Ticket;

public class TicketMapper {
    public TicketDTO mapTicket(Ticket ticket) {
        return new TicketDTO.TicketDTOBuilder()
                .ticketId(ticket.getTicketId())
                .price(ticket.getPrice())
                .travelClass(ticket.getTravelClass())
                .ticketStatus(ticket.getStatus())
                .build();
    }

}
