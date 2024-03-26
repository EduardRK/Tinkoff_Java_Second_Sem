package edu.java.service.scrapper_body.clients_body;

import java.net.URI;
import java.time.OffsetDateTime;

public record Response(
    URI source,
    String author,
    String message,
    OffsetDateTime date
) {
}
