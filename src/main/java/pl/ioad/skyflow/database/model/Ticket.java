package pl.ioad.skyflow.database.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@JsonFormat(pattern="yyyy-MM-dd")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @NonNull
    private String travelClass;

    @NonNull
    private String seatNumber;

    @NonNull
    private Double price;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String identificationNumber;

    @NonNull
    private TicketStatus status;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private UpcomingFlight flight;

}

