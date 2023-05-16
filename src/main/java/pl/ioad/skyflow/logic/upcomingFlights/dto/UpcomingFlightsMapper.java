package pl.ioad.skyflow.logic.upcomingFlights.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.UpcomingFlight;


@Service
public class UpcomingFlightsMapper {
    public UpcomingFlightsDTO map(UpcomingFlight flight) {
        return new UpcomingFlightsDTO.UpcomingFlightsDTOBuilder()
                .departureDate(flight.getDepartureDate())
                .arrivalAirport(flight.getArrivalAirport())
                .departureAirport(flight.getDepartureAirport())
                .capacity(flight.getCapacity())
                .build();
    }
}
