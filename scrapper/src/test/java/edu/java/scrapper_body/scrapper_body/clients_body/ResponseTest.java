package edu.java.scrapper_body.scrapper_body.clients_body;

import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseTest {
    @Test
    void author() throws URISyntaxException {
        Response response = new Response() {
            @Override
            public String author() {
                return "Author";
            }

            @Override
            public String message() {
                return "Message";
            }

            @Override
            public OffsetDateTime date() {
                return OffsetDateTime.MAX;
            }
        };

        Assertions.assertEquals(
            "Author",
            response.author()
        );
    }

    @Test
    void message() throws URISyntaxException {
        Response response = new Response() {
            @Override
            public String author() {
                return "Author";
            }

            @Override
            public String message() {
                return "Message";
            }

            @Override
            public OffsetDateTime date() {
                return OffsetDateTime.MAX;
            }
        };

        Assertions.assertEquals(
            "Message",
            response.message()
        );
    }

    @Test
    void date() throws URISyntaxException {
        Response response = new Response() {
            @Override
            public String author() {
                return "Author";
            }

            @Override
            public String message() {
                return "Message";
            }

            @Override
            public OffsetDateTime date() {
                return OffsetDateTime.MAX;
            }
        };

        Assertions.assertEquals(
            OffsetDateTime.MAX,
            response.date()
        );
    }
}
