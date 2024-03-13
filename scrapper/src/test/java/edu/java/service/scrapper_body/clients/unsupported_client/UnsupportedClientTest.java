package edu.java.service.scrapper_body.clients.unsupported_client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import edu.java.service.scrapper_body.clients.unsupported_client.UnsupportedClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnsupportedClientTest {

    @Test
    void newUpdates() throws URISyntaxException {
        UnsupportedClient unsupportedClient = new UnsupportedClient();

        Assertions.assertEquals(
            new ArrayList<>(),
            unsupportedClient.newUpdates(new URI("Unsupportedlink.com"))
        );
    }
}
