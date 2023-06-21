package pl.ioad.skyflow.logic.upcomingflights;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.UpcomingFlight;
import pl.ioad.skyflow.database.repository.UpcomingFlightRepository;
import pl.ioad.skyflow.logic.flight.payload.FlightSearchRequest;
import pl.ioad.skyflow.logic.upcomingflights.dto.UpcomingFlightsDto;
import pl.ioad.skyflow.logic.upcomingflights.dto.UpcomingFlightsMapper;
import pl.ioad.skyflow.logic.upcomingflights.payload.request.UpcomingFlightRequest;
import pl.ioad.skyflow.logic.upcomingflights.payload.response.UpcomingFlightsResponse;

@Service
@RequiredArgsConstructor
public class UpcomingFlightsService {

    private final UpcomingFlightRepository upcomingFlightRepository;
    private final UpcomingFlightsMapper upcomingFlightsMapper;

    public UpcomingFlightsResponse addFlight(UpcomingFlightRequest request) {
        UpcomingFlight flight = UpcomingFlight.builder()
                .icao24(request.icao24())
                .departureDate(request.departureDate())
                .departureAirport(request.departureAirport())
                .arrivalAirport(request.arrivalAirport())
                .capacity(request.capacity()).build();
        upcomingFlightRepository.save(flight);
        return new UpcomingFlightsResponse("Succesfully added flight");
    }

    public List<UpcomingFlightsDto> getFlights(FlightSearchRequest request) {
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return upcomingFlightRepository.findAllByDepartureAirportAndDepartureDateBetween(
                            request.departureAirport(),
                            dateFormatter.parse(request.begin()),
                            dateFormatter.parse(request.end()))
                    .stream()
                    .map(upcomingFlightsMapper::map)
                    .toList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public UpcomingFlightsResponse clearFlights() {
        upcomingFlightRepository.deleteAll();
        return new UpcomingFlightsResponse("Succesfully cleared flights");
    }
}
