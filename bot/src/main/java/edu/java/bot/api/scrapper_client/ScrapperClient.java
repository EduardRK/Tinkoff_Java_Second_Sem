package edu.java.bot.api.scrapper_client;

import edu.java.bot.api.requests.AddLinkRequest;
import edu.java.bot.api.requests.RemoveLinkRequest;
import edu.java.bot.api.responses.LinkResponse;
import edu.java.bot.api.responses.ListLinksResponse;

public interface ScrapperClient {
    void registerChat(int id);

    void deleteChat(int id);

    ListLinksResponse allTrackedLinks(int id);

    LinkResponse startTrackLink(int id, AddLinkRequest addLinkRequest);

    LinkResponse stopTrackLink(int id, RemoveLinkRequest removeLinkRequest);
}
