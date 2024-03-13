package edu.java.service.scrapper_body.clients_body;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseTest {
    @Test
    void source() throws URISyntaxException {
        Response response = new Response(
            new URI("Somelink.com"),
            "Author",
            "Message",
            OffsetDateTime.MAX
        );

        Assertions.assertEquals(
            new URI("Somelink.com"),
            response.source()
        );
    }

    @Test
    void author() throws URISyntaxException {
        Response response = new Response(
            new URI("Somelink.com"),
            "Author",
            "Message",
            OffsetDateTime.MAX
        );

        Assertions.assertEquals(
            "Author",
            response.author()
        );
    }

    @Test
    void message() throws URISyntaxException {
        Response response = new Response(
            new URI("Somelink.com"),
            "Author",
            "Message",
            OffsetDateTime.MAX
        );

        Assertions.assertEquals(
            "Message",
            response.message()
        );
    }

    @Test
    void date() throws URISyntaxException {
        Response response = new Response(
            new URI("Somelink.com"),
            "Author",
            "Message",
            OffsetDateTime.MAX
        );

        Assertions.assertEquals(
            OffsetDateTime.MAX,
            response.date()
        );
    }
}
