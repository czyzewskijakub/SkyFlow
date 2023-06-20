package pl.ioad.skyflow.logic.ticket.dto;

import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public TicketDTO map(Reservation reservation) {
        return new TicketDTO
                .ReservationDTOBuilder()
                .reservationId(reservation.getReservationId())
                .build();
    }

}
