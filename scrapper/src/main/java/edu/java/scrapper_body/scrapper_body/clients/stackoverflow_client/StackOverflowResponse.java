package edu.java.scrapper_body.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponse(
    @JsonProperty("items")
    List<Answer> answers
) {
    public record Answer(
        @JsonProperty("owner")
        Owner owner,
        @JsonProperty("body")
        String message,
        @JsonProperty("creation_date")
        OffsetDateTime date
    ) {
        public String author() {
            return owner.author();
        }
    }

    public record Owner(
        @JsonProperty("display_name")
        String author
    ) {
    }
}
