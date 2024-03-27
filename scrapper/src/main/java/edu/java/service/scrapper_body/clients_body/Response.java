package edu.java.service.scrapper_body.clients_body;

import java.time.OffsetDateTime;

public interface Response {
    String author();

    String message();

    OffsetDateTime date();
}
