package edu.java.bot.service.scrapper_client;

import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import reactor.core.publisher.Mono;

public interface ScrapperClient {
    Mono<Void> registerChat(long id);

    Mono<Void> deleteChat(long id);

    Mono<ListLinksResponse> allTrackedLinks(long id);

    Mono<LinkResponse> startTrackLink(long id, AddLinkRequest addLinkRequest);

    Mono<LinkResponse> stopTrackLink(long id, RemoveLinkRequest removeLinkRequest);
}
