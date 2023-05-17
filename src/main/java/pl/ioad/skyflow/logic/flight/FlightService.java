package pl.ioad.skyflow.logic.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.logic.flight.dto.FlightDTO;
import pl.ioad.skyflow.logic.flight.opensky.Credentials;
import pl.ioad.skyflow.logic.flight.payload.FlightSearchRequest;

import java.util.List;

import static pl.ioad.skyflow.logic.flight.opensky.Connection.sendGetRequest;
import static pl.ioad.skyflow.logic.flight.opensky.Endpoint.DEPARTURE;

@Service
@RequiredArgsConstructor
public class FlightService {

    private static final String AIRPORT_CODE = "airport";
    private static final String BEGIN_TIME = "begin";
    private static final String END_TIME = "end";

    private final Credentials credentials;
    private final ObjectMapper mapper;

    /**
     * @param request Flight search request
     * @return {@link List}&lt;{@link FlightDTO}&gt;
     */
    public List<FlightDTO> findFlight(FlightSearchRequest request) {
        List<NameValuePair> requestParams = List.of(
                new BasicNameValuePair(AIRPORT_CODE, request.departureAirport()),
                new BasicNameValuePair(BEGIN_TIME, Integer.toString(request.begin())),
                new BasicNameValuePair(END_TIME, Integer.toString(request.end()))
        );
        String response = sendGetRequest(DEPARTURE, credentials, requestParams);
        try {
            return mapper.readValue(response, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
