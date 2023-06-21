package pl.ioad.skyflow.logic.ticket.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
@Getter
@Setter
@AllArgsConstructor
public class FlightRequest {

    @NonNull
    @Schema(description = "flight id user wants to reserve", example = "1")
    private Long flightId;
    @NonNull
    @Schema(description = "seat number wants to reserve", example = "1")
    private String seatNumber;
    @NonNull
    @Schema(description = "price of ticket in EUR", example = "100")
    private Double price;
    @NonNull
    @Schema(description = "class", example = "ECONOMY")
    private String travelClass;
    @NonNull
    @Schema(description = "ticket owner's name", example = "Rafał")
    private String name;
    @NonNull
    @Schema(description = "ticket owner's surname", example = "Komorowski")
    private String surname;
    @NonNull
    @Schema(description = "number from id document", example = "PL2314132")
    private String identificationNumber;

}
