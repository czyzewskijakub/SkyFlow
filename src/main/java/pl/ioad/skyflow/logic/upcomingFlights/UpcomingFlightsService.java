package pl.ioad.skyflow.logic.upcomingFlights;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Reservation;
import pl.ioad.skyflow.database.model.UpcomingFlight;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.ReservationRepository;
import pl.ioad.skyflow.database.repository.UpcomingFlightRepository;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.reservation.dto.Mapper;
import pl.ioad.skyflow.logic.reservation.payload.request.FlightRequest;
import pl.ioad.skyflow.logic.reservation.payload.response.ReservationResponse;
import pl.ioad.skyflow.logic.upcomingFlights.dto.UpcomingFlightsDTO;
import pl.ioad.skyflow.logic.upcomingFlights.dto.UpcomingFlightsMapper;
import pl.ioad.skyflow.logic.upcomingFlights.payload.request.UpcomingFlightRequest;
import pl.ioad.skyflow.logic.upcomingFlights.payload.response.UpcomingFlightsResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpcomingFlightsService {
    private final UpcomingFlightRepository upcomingFlightRepository;
    private final UpcomingFlightsMapper upcomingFlightsMapper;

    public UpcomingFlightsResponse addFlight(UpcomingFlightRequest request, HttpServletRequest http) {
        UpcomingFlight flight = UpcomingFlight.builder()
                .departureDate(request.getDepartureDate())
                .departureAirport(request.getDepartureAirport())
                .arrivalAirport(request.getArrivalAirport())
                .capacity(request.getCapacity()).build();
        upcomingFlightRepository.save(flight);
        return new UpcomingFlightsResponse("Succesfully added flight");
    }

    public List<UpcomingFlightsDTO> getFlights() {
        return upcomingFlightRepository.findAll().stream().map(upcomingFlightsMapper::map).toList();
    }

    public UpcomingFlightsResponse clearFlights() {
        upcomingFlightRepository.deleteAll();
        return new UpcomingFlightsResponse("Succesfully cleared flights");
    }
}
