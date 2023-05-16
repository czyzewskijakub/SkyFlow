package pl.ioad.skyflow.database.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@JsonFormat(pattern="yyyy-MM-dd")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @NonNull
    private Date departureDate;

    @NonNull
    private Date arrivalDate;

    @NonNull
    private String departureAirport;

    @NonNull
    private String arrivalAirport;

    @NonNull
    private String airline;

    @NonNull
    private String travelClass;

    @NonNull
    private String seatNumber;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
