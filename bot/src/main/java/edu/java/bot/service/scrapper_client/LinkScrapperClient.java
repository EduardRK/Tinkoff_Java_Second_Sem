package edu.java.bot.service.scrapper_client;

import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.ApiErrorResponse;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public final class LinkScrapperClient implements ScrapperClient {
    private static final String TG_CHAT_ID = "/tg-chat/";
    private static final String LINKS = "/links";
    private static final String ID = "Tg-Chat-Id";
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
    public Mono<Void> registerChat(long id) {
        log.info("Send chat register request");
        return webClient
            .post()
            .uri(TG_CHAT_ID + id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> deleteChat(long id) {
        log.info("Send chat delete request");
        return webClient
            .delete()
            .uri(TG_CHAT_ID + id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<ListLinksResponse> allTrackedLinks(long id) {
        log.info("Send all tracked links request");
        return webClient
            .get()
            .uri(LINKS)
            .header(ID, String.valueOf(id))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .bodyToMono(ListLinksResponse.class);
    }

    @Override
    public Mono<LinkResponse> startTrackLink(long id, AddLinkRequest addLinkRequest) {
        log.info("Send start track link request");
        return webClient
            .post()
            .uri(LINKS)
            .header(ID, String.valueOf(id))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(addLinkRequest)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .bodyToMono(LinkResponse.class);
    }

    @Override
    public Mono<LinkResponse> stopTrackLink(long id, RemoveLinkRequest removeLinkRequest) {
        log.info("Send stop track link request");
        return webClient
            .method(HttpMethod.DELETE)
            .uri(LINKS)
            .header(ID, String.valueOf(id))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(removeLinkRequest)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new ApiErrorException(apiErrorResponse)))
            )
            .bodyToMono(LinkResponse.class);
    }
}
