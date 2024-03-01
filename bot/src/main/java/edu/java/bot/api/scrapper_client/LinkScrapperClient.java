package edu.java.bot.api.scrapper_client;

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

@Component
public final class LinkScrapperClient implements ScrapperClient {
    private static final String TG_CHAT_ID = "tg_chat/{id}";
    private static final String LINKS = "/links";
    private static final String ID = "id";
    private static final String BASE_URI = "http://localhost:8080";
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
                httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
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
                httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
            )
            .onStatus(
                httpStatusCode -> httpStatusCode.equals(HttpStatus.NOT_FOUND),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
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
                httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
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
                httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
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
                httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
            )
            .onStatus(
                httpStatusCode -> httpStatusCode.equals(HttpStatus.NOT_FOUND),
                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
