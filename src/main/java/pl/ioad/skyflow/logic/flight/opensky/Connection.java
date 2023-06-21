package pl.ioad.skyflow.logic.flight.opensky;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

public abstract class Connection {

    private static final String OPENSKY_URL = "https://opensky-network.org/api";

    public static String sendGetRequest(Endpoint endpoint,
                                        Credentials credentials,
                                        List<NameValuePair> requestParams) {

        HttpGet httpGet = new HttpGet(OPENSKY_URL + endpoint.getUrl());
        httpGet.setHeader("Authorization", credentials.getBasicAuthenticationHeader());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            URI uri = new URIBuilder(httpGet.getUri())
                    .addParameters(requestParams)
                    .build();
            httpGet.setUri(uri);

            return httpClient.execute(httpGet, new BasicHttpClientResponseHandler());

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
