package edu.java.bot.service.bot_body.generators;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.requests.LinkUpdateRequest;
import java.util.List;

public final class SendMessageFromLinkUpdateGenerator implements Generator<List<SendMessage>, LinkUpdateRequest> {
    private static final ParseMode PARSE_MODE = ParseMode.Markdown;

    public SendMessageFromLinkUpdateGenerator() {

    }

    @Override
    public List<SendMessage> nextObject(LinkUpdateRequest object) {
        return object.tgChatIds()
            .stream()
            .map(id -> new SendMessage(id, message(object)).parseMode(PARSE_MODE))
            .toList();
    }

    private String message(LinkUpdateRequest linkUpdateRequest) {
        return "New update: " + '\n'
            + "Link: " + linkUpdateRequest.uri() + '\n'
            + '\n'
            + linkUpdateRequest.description();
    }
}
