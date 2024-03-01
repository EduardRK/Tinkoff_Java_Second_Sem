package edu.java.api.bot_client;

import edu.java.api.requests.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
