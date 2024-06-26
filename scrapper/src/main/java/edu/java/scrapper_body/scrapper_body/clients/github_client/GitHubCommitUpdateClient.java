package edu.java.scrapper_body.scrapper_body.clients.github_client;

import edu.java.scrapper_body.scrapper_body.clients.unsupported_client.UnsupportedClient;
import edu.java.scrapper_body.scrapper_body.clients_body.AbstractClient;
import edu.java.scrapper_body.scrapper_body.clients_body.Client;
import edu.java.scrapper_body.scrapper_body.clients_body.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

public final class GitHubCommitUpdateClient extends AbstractClient {
    private static final String BASE_URI = "https://api.github.com";
    private static final String HOST = "github.com";

    public GitHubCommitUpdateClient(String baseURI, Retry retry, Client nextClient) {
        super(WebClient.create(baseURI), retry, nextClient);
    }

    public GitHubCommitUpdateClient(Client nextClient, Retry retry) {
        this(BASE_URI, retry, nextClient);
    }

    public GitHubCommitUpdateClient(String baseURI, Retry retry) {
        this(baseURI, retry, new UnsupportedClient());
    }

    public GitHubCommitUpdateClient(Retry retry) {
        this(BASE_URI, retry, new UnsupportedClient());
    }

    @Override
    public List<? extends Response> newUpdates(URI uri) {
        if (notValid(uri)) {
            return nextClient.newUpdates(uri);
        }

        List<GitHubResponse> gitHubResponses = webClient
            .get()
            .uri("/repos" + uri.getPath() + "/commits")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<GitHubResponse>>() {
            })
            .onErrorReturn(new ArrayList<>())
            .retryWhen(retry)
            .block();

        if (Objects.requireNonNull(gitHubResponses).isEmpty()) {
            return new ArrayList<>();
        }

        return gitHubResponses;
    }

    @Override
    protected boolean notValid(URI uri) {
        return !(uri.getHost() == null || uri.getHost().equals(HOST));
    }
}
