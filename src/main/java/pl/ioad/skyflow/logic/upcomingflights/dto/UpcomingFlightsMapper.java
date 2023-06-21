package pl.ioad.skyflow.logic.upcomingflights.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.UpcomingFlight;


@Service
public class UpcomingFlightsMapper {
    public UpcomingFlightsDto map(UpcomingFlight flight) {
        return new UpcomingFlightsDto.UpcomingFlightsDtoBuilder()
                .icao24(flight.getIcao24())
                .departureDate(flight.getDepartureDate())
                .arrivalAirport(flight.getArrivalAirport())
                .departureAirport(flight.getDepartureAirport())
                .capacity(flight.getCapacity())
                .build();
    }
}
