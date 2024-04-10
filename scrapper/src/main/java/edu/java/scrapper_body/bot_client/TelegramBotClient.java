package edu.java.scrapper_body.bot_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import edu.java.responses.ApiErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public final class TelegramBotClient implements BotClient {
    private static final String BASE_URI = "http://localhost:8090";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final WebClient webClient;

    public TelegramBotClient(String baseUri) {
        this.webClient = WebClient.create(baseUri);
    }

    @Autowired
    public TelegramBotClient() {
        this(BASE_URI);
    }

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        webClient.post()
            .uri("/update")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(BadRequestException.class).flatMap(Mono::error)
            )
            .bodyToMono(Void.class)
            .onErrorResume(e -> {
                if (e instanceof WebClientResponseException responseException) {
                    String responseBody = responseException.getResponseBodyAsString();
                    try {

                        ApiErrorResponse apiErrorResponse = OBJECT_MAPPER.readValue(
                            responseBody,
                            ApiErrorResponse.class
                        );

                        log.info(apiErrorResponse.code());
                        log.info(apiErrorResponse.description());
                        apiErrorResponse.stacktrace().forEach(log::info);

                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                return Mono.empty();
            })
            .block();
    }

    @Override
    public void sendUpdates(List<LinkUpdateRequest> linkUpdateRequestList) {
        linkUpdateRequestList.parallelStream().forEach(this::sendUpdate);
    }
}
