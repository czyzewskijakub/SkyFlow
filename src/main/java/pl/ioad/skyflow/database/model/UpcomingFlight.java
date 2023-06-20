package pl.ioad.skyflow.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@JsonFormat(pattern="yyyy-MM-dd HH:mm")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class UpcomingFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long flightId;

    @NonNull
    private String icao24;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date departureDate;

    @NonNull
    private String departureAirport;

    @NonNull
    private String arrivalAirport;

    @NonNull
    private Integer capacity;
}
