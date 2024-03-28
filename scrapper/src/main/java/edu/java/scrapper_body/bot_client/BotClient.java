package edu.java.scrapper_body.bot_client;

import edu.java.requests.LinkUpdateRequest;
import java.util.List;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdateRequest);

    void sendUpdates(List<LinkUpdateRequest> linkUpdateRequestList);
}
