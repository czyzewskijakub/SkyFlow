package pl.ioad.skyflow.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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

    private String arrivalAirport;

    @NonNull
    private Integer capacity;
}
