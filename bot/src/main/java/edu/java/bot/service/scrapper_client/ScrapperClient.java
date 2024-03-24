package edu.java.bot.service.scrapper_client;

import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;

public interface ScrapperClient {
    void registerChat(long id);

    void deleteChat(long id);

    ListLinksResponse allTrackedLinks(long id);

    LinkResponse startTrackLink(long id, AddLinkRequest addLinkRequest);

    LinkResponse stopTrackLink(long id, RemoveLinkRequest removeLinkRequest);
}
