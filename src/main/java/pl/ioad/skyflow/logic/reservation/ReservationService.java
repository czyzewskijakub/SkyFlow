package pl.ioad.skyflow.logic.reservation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Reservation;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.ReservationRepository;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.exception.type.ForbiddenException;
import pl.ioad.skyflow.logic.exception.type.InvalidBusinessArgumentException;
import pl.ioad.skyflow.logic.reservation.dto.Mapper;
import pl.ioad.skyflow.logic.reservation.dto.ReservationDTO;
import pl.ioad.skyflow.logic.reservation.payload.request.CancelRequest;
import pl.ioad.skyflow.logic.reservation.payload.request.FlightRequest;
import pl.ioad.skyflow.logic.reservation.payload.response.ReservationResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final Mapper mapper;
    public ReservationResponse bookFlight(FlightRequest request, HttpServletRequest http) {
        User user = extractUser(http);

        Reservation reservation = Reservation.builder()
                .departureDate(request.getDepartureDate())
                .arrivalDate(request.getArrivalDate())
                .departureAirport(request.getDepartureAirport())
                .arrivalAirport(request.getArrivalAirport())
                .airline(request.getAirline())
                .travelClass(request.getTravelClass())
                .seatNumber(request.getSeatNumber())
                .user(user)
                .build();

        reservationRepository.save(reservation);

        return new ReservationResponse("Successfully booked flight");
    }

    public ReservationResponse cancelFlight(CancelRequest request, HttpServletRequest http) {
        User user = extractUser(http);

        var reservation = reservationRepository.findById(request.reservationId());
        if (reservation.isEmpty())
            throw new InvalidBusinessArgumentException("This reservation does not exist");
        if (!reservation.get().getUser().getUserId().equals(user.getUserId()))
            throw new InvalidBusinessArgumentException("You were not booked for this flight");

        reservationRepository.delete(reservation.get());

        return new ReservationResponse("Successfully canceled flight");
    }

    public List<ReservationDTO> retrieveReservations(HttpServletRequest http) {
        User user = extractUser(http);
        return reservationRepository.findAll().stream()
                .filter(e -> e.getUser().getUserId().equals(user.getUserId()))
                .map(mapper::map)
                .toList();
    }

    public User extractUser(HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null)
            throw new ForbiddenException("You are not authorized");
        token = token.substring("Bearer ".length());

        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isEmpty())
            throw new ForbiddenException("You need to be logged in");
        return user.get();
    }

}
