package edu.java.scrapper_body.scrapper_body.clients.unsupported_client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
