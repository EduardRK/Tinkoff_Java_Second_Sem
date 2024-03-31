package edu.java.scrapper_body.scrapper_body.clients.stackoverflow_client;

import edu.java.scrapper_body.scrapper_body.clients.unsupported_client.UnsupportedClient;
import edu.java.scrapper_body.scrapper_body.clients_body.AbstractClient;
import edu.java.scrapper_body.scrapper_body.clients_body.Client;
import edu.java.scrapper_body.scrapper_body.clients_body.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

public final class StackOverflowQuestionClient extends AbstractClient {
    private static final String BASE_URI = "https://api.stackexchange.com/2.3";
    private static final String HOST = "stackoverflow.com";
    private static final String FILTERS = "?order=desc&sort=votes&site=stackoverflow&filter=!nNPvSNdWme";
    private static final Pattern PATTERN = Pattern.compile("^(.+questions)/(\\d+)/(.*)$");

    public StackOverflowQuestionClient(String baseURI, Retry retry, Client nextClient) {
        super(WebClient.create(baseURI), retry, nextClient);
    }

    public StackOverflowQuestionClient(Client nextClient, Retry retry) {
        this(BASE_URI, retry, nextClient);
    }

    public StackOverflowQuestionClient(String baseURI, Retry retry) {
        this(baseURI, retry, new UnsupportedClient());
    }

    public StackOverflowQuestionClient(Retry retry) {
        this(BASE_URI, retry, new UnsupportedClient());
    }

    @Override
    public List<? extends Response> newUpdates(URI uri) {
        if (notValid(uri)) {
            return nextClient.newUpdates(uri);
        }

        Matcher matcher = PATTERN.matcher(uri.toString());
        matcher.matches();

        StackOverflowResponse stackOverflowResponse = webClient
            .get()
            .uri("/questions/" + matcher.group(2) + "/answers" + FILTERS)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .onErrorReturn(new StackOverflowResponse(new ArrayList<>()))
            .retryWhen(retry)
            .block();

        if (Objects.requireNonNull(stackOverflowResponse).answers().isEmpty()) {
            return new ArrayList<>();
        }

        return stackOverflowResponse.answers();
    }

    @Override
    protected boolean notValid(@NotNull URI uri) {
        return !(uri.getHost() == null || uri.getHost().equals(HOST));
    }
}
