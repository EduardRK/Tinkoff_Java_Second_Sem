package edu.java.bot.service.scrapper_client;

import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;

public interface ScrapperClient {
    void registerChat(int id);

    void deleteChat(int id);

    ListLinksResponse allTrackedLinks(int id);

    LinkResponse startTrackLink(int id, AddLinkRequest addLinkRequest);

    LinkResponse stopTrackLink(int id, RemoveLinkRequest removeLinkRequest);
}
