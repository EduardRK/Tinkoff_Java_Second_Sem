package edu.java.scrapper_body.scrapper_body.clients.github_client;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.service.scrapper_body.clients_body.Response;
import java.time.OffsetDateTime;

public record GitHubResponse(
    @JsonProperty("commit")
    Commit commit
) implements Response {
    @Override
    public String author() {
        return commit.committer().author();
    }

    @Override
    public String message() {
        return commit.message();
    }

    @Override
    public OffsetDateTime date() {
        return commit.committer().date();
    }

    public record Commit(
        @JsonProperty("committer")
        Committer committer,
        @JsonProperty("message")
        String message
    ) {
    }

    public record Committer(
        @JsonProperty("name")
        String author,
        @JsonProperty("date")
        OffsetDateTime date
    ) {
    }
}
