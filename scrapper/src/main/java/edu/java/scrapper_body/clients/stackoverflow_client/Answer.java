package edu.java.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.OffsetDateTime;

@JsonDeserialize(using = AnswerDeserializer.class)
public record Answer(
    String author,
    String message,
    OffsetDateTime date
) {
}
