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

    public List<FlightDTO> findFlight(String airport, Integer begin, Integer end) {
        List<NameValuePair> requestParams = List.of(
                new BasicNameValuePair(AIRPORT_CODE, airport),
                new BasicNameValuePair(BEGIN_TIME, Integer.toString(begin)),
                new BasicNameValuePair(END_TIME, Integer.toString(end))
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
