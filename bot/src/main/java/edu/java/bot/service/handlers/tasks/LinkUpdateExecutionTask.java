package edu.java.bot.service.handlers.tasks;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.bot_body.generators.Generator;
import edu.java.bot.service.bot_body.generators.SendMessageFromLinkUpdateGenerator;
import edu.java.requests.LinkUpdateRequest;
import java.util.List;

public final class LinkUpdateExecutionTask implements Runnable {
    private static final Generator<List<SendMessage>, LinkUpdateRequest>
        GENERATOR = new SendMessageFromLinkUpdateGenerator();
    private final TelegramBot telegramBot;
    private final LinkUpdateRequest linkUpdateRequest;

    public LinkUpdateExecutionTask(
        TelegramBot telegramBot,
        LinkUpdateRequest linkUpdateRequest
    ) {
        this.telegramBot = telegramBot;
        this.linkUpdateRequest = linkUpdateRequest;
    }

    @Override
    public void run() {
        GENERATOR.nextObject(linkUpdateRequest).forEach(telegramBot::execute);
    }
}
