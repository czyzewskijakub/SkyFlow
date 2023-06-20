package pl.ioad.skyflow.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_record_id")
    private Long cartRecordId;

    @NonNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @NonNull
    private Date additionDate;
}
