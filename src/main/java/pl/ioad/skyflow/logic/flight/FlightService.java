package pl.ioad.skyflow.logic.flight;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
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

    public String findFlight(String airport, Integer begin, Integer end) {
        List<NameValuePair> requestParams = List.of(
                new BasicNameValuePair(AIRPORT_CODE, airport),
                new BasicNameValuePair(BEGIN_TIME, Integer.toString(begin)),
                new BasicNameValuePair(END_TIME, Integer.toString(end))
        );
        return sendGetRequest(DEPARTURE, credentials, requestParams);
    }

}
