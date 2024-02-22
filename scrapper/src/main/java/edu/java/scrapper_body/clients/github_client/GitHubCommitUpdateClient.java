package edu.java.scrapper_body.clients.github_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper_body.clients.unsupported_client.UnsupportedClient;
import edu.java.scrapper_body.clients_body.AbstractClient;
import edu.java.scrapper_body.clients_body.Client;
import edu.java.scrapper_body.clients_body.Response;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public final class GitHubCommitUpdateClient extends AbstractClient {
    private static final String BASE_URI = "https://api.github.com";
    private static final String HOST = "github.com";
    private static final String COMMIT = "commit";
    private static final String COMMITTER = "committer";

    public GitHubCommitUpdateClient(String baseURI, Client nextClient) {
        super(WebClient.create(baseURI), nextClient);
    }

    public GitHubCommitUpdateClient(Client nextClient) {
        this(BASE_URI, nextClient);
    }

    public GitHubCommitUpdateClient(String baseURI) {
        this(baseURI, new UnsupportedClient());
    }

    public GitHubCommitUpdateClient() {
        this(BASE_URI, new UnsupportedClient());
    }

    @Override
    public List<Response> newUpdates(URI uri) throws JsonProcessingException {
        if (notValid(uri)) {
            return nextClient.newUpdates(uri);
        }

        String json = webClient
            .get()
            .uri("/repos" + uri.getPath() + "/commits")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorReturn("")
            .block();

        if (Objects.requireNonNull(json).isEmpty()) {
            return new ArrayList<>();
        }

        return commitsFromJSON(json)
            .stream()
            .map(commit -> new Response(uri, commit.author, commit.message, commit.date))
            .toList();
    }

    @Override
    protected boolean notValid(URI uri) {
        return !uri.getHost().equals(HOST);
    }

    private List<Commit> commitsFromJSON(String json) throws JsonProcessingException {
        List<Commit> commits = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        for (int i = 0; i < root.size(); i++) {
            JsonNode arrayNode = root.get(i);
            commits.add(
                new Commit(
                    arrayNode.get(COMMIT).get(COMMITTER).get("name").asText(),
                    arrayNode.get(COMMIT).get("message").asText(),
                    OffsetDateTime.parse(arrayNode.get(COMMIT).get(COMMITTER).get("date").asText())
                )
            );
        }

        return commits;
    }

    private record Commit(
        String author,
        String message,
        OffsetDateTime date
    ) {

    }
}
