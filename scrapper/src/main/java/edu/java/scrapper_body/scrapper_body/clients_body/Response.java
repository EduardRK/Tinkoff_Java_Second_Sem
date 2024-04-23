package edu.java.scrapper_body.scrapper_body.clients_body;

import java.time.OffsetDateTime;

public interface Response {
    String author();

    String message();

    OffsetDateTime date();
}
