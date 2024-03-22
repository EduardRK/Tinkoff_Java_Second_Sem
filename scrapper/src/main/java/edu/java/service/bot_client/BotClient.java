package edu.java.service.bot_client;

import edu.java.requests.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
