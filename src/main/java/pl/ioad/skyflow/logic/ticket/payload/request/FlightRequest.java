package pl.ioad.skyflow.logic.ticket.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@JsonFormat(pattern="yyyy-MM-dd HH:mm")
@Getter
@Setter
@AllArgsConstructor
public class FlightRequest {

    private Long flightId;
    private String seatNumber;
    private Double price;
    private String travelClass;
    private String name;
    private String surname;
    private String identificationNumber;
    private String status;

}
