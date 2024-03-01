package edu.java.api.bot_client;

import edu.java.exceptions.ChatNotRegisteredException;
import edu.java.exceptions.UriNotTrackedException;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
                httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                clientResponse -> clientResponse.bodyToMono(UriNotTrackedException.class)
            )
            .onStatus(
                httpStatusCode -> httpStatusCode.equals(HttpStatus.NOT_FOUND),
                clientResponse -> clientResponse.bodyToMono(ChatNotRegisteredException.class)
            )
            .bodyToMono(Void.class)
            .block();
    }
}
