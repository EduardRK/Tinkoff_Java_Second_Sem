package edu.java.scrapper_body.scrapper_body.clients.github_client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponse(
    @JsonProperty("commit")
    Commit commit
) {
    public String author() {
        return commit.committer().author();
    }

    public String message() {
        return commit.message();
    }

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
