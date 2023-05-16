package pl.ioad.skyflow.logic.reservation.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Reservation;

@Service
public class ReservationMapper {
    public ReservationDTO map(Reservation reservation) {
        return new ReservationDTO
                .ReservationDTOBuilder()
                .reservationId(reservation.getReservationId())
                .build();
    }

}
