package edu.java.bot.service.scrapper_client;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public final class LinkScrapperClient implements ScrapperClient {
    private static final String TG_CHAT_ID = "tg_chat/{id}";
    private static final String LINKS = "/links";
    private static final String ID = "id";
    private static final String BASE_URI = "http://localhost:80";
    private final WebClient webClient;

    public LinkScrapperClient(String baseUri) {
        this.webClient = WebClient.create(baseUri);
    }

    @Autowired
    public LinkScrapperClient() {
        this(BASE_URI);
    }

    @Override
    public void registerChat(int id) {
        webClient.post()
            .uri(TG_CHAT_ID, id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(BadRequestException.class).flatMap(Mono::error)
            )
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public void deleteChat(int id) {
        webClient.delete()
            .uri(TG_CHAT_ID, id)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(BadRequestException.class).flatMap(Mono::error)
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(NotFoundException.class).flatMap(Mono::error)
            )
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public ListLinksResponse allTrackedLinks(int id) {
        return webClient.get()
            .uri(LINKS)
            .header(ID, String.valueOf(id))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(BadRequestException.class).flatMap(Mono::error)
            )
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse startTrackLink(int id, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(LINKS)
            .header(ID, String.valueOf(id))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(addLinkRequest)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(BadRequestException.class).flatMap(Mono::error)
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse stopTrackLink(int id, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS)
            .header(ID, String.valueOf(id))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(removeLinkRequest)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(BadRequestException.class).flatMap(Mono::error)
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(NotFoundException.class).flatMap(Mono::error)
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
