package edu.java.service.bot_client;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public final class TelegramBotClient implements BotClient {
    private static final String BASE_URI = "http://localhost:8090";
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
            .block();
    }
}
