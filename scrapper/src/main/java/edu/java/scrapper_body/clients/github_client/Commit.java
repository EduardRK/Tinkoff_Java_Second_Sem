package edu.java.scrapper_body.clients.github_client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.OffsetDateTime;

@JsonDeserialize(using = CommitDeserializer.class)
public record Commit(
    String author,
    String message,
    OffsetDateTime date
) {

}
