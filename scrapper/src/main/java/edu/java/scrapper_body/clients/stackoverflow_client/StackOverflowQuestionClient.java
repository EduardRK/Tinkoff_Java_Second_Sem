package edu.java.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper_body.clients.unsupported_client.UnsupportedClient;
import edu.java.scrapper_body.clients_body.AbstractClient;
import edu.java.scrapper_body.clients_body.Client;
import edu.java.scrapper_body.clients_body.Response;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public final class StackOverflowQuestionClient extends AbstractClient {
    private static final String BASE_URI = "https://api.stackexchange.com/2.3";
    private static final String HOST = "stackoverflow.com";
    private static final String FILTERS = "?order=desc&sort=votes&site=stackoverflow&filter=!nNPvSNdWme";
    private static final Pattern PATTERN = Pattern.compile("^(.+questions)/(\\d+)/(.*)$");

    public StackOverflowQuestionClient(String baseURI, Client nextClient) {
        super(WebClient.create(baseURI), nextClient);
    }

    public StackOverflowQuestionClient(Client nextClient) {
        this(BASE_URI, nextClient);
    }

    public StackOverflowQuestionClient(String baseURI) {
        this(baseURI, new UnsupportedClient());
    }

    public StackOverflowQuestionClient() {
        this(BASE_URI, new UnsupportedClient());
    }

    @Override
    public List<Response> newUpdates(URI uri) throws JsonProcessingException {
        if (notValid(uri)) {
            return nextClient.newUpdates(uri);
        }

        Matcher matcher = PATTERN.matcher(uri.toString());
        matcher.matches();

        String json = webClient
            .get()
            .uri("/questions/" + matcher.group(2) + "/answers" + FILTERS)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorReturn("")
            .block();

        if (Objects.requireNonNull(json).isEmpty()) {
            return new ArrayList<>();
        }

        return answersFromJSON(json)
            .stream()
            .map(answer -> new Response(uri, answer.author, answer.message, answer.date))
            .toList();
    }

    @Override
    protected boolean notValid(@NotNull URI uri) {
        return !uri.getHost().equals(HOST);
    }

    private @NotNull List<Answer> answersFromJSON(String json) throws JsonProcessingException {
        List<Answer> answers = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json).get("items");

        for (int i = 0; i < root.size(); ++i) {
            JsonNode arrayNode = root.get(i);

            answers.add(
                new Answer(
                    arrayNode.get("owner").get("display_name").asText(),
                    arrayNode.get("body").asText(),
                    OffsetDateTime.ofInstant(
                        Instant.ofEpochSecond(
                            arrayNode.get("creation_date").asInt()
                        ),
                        ZoneOffset.UTC
                    )
                )
            );
        }

        return answers;
    }

    private record Answer(
        String author,
        String message,
        OffsetDateTime date
    ) {

    }
}
