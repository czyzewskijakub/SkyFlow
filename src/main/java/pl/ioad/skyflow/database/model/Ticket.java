package pl.ioad.skyflow.database.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

//    @ManyToOne
//    @JoinColumn(name = "flight_id")
//    private UpcomingFlight flight;

    @NonNull
    private Double price;

    @NonNull
    private Boolean availability;

    @NonNull
    private String seat;


}
