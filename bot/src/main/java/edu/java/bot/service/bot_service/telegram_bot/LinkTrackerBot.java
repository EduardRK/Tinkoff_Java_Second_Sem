package edu.java.bot.service.bot_service.telegram_bot;

import edu.java.bot.service.bot_service.handlers.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LinkTrackerBot implements TrackerBot {
    private final DefaultTelegramBotComponent defaultTelegramBotComponent;
    private final UpdateHandler handler;

    @Autowired
    public LinkTrackerBot(
        DefaultTelegramBotComponent defaultTelegramBotComponent,
        UpdateHandler handler
    ) {
        this.defaultTelegramBotComponent = defaultTelegramBotComponent;
        this.handler = handler;
    }

    @Override
    public void close() {
        defaultTelegramBotComponent.telegramBot()
            .removeGetUpdatesListener();
    }

    @Override
    public void start() {
        defaultTelegramBotComponent.telegramBot()
            .setUpdatesListener(
                new CommandUpdateListener(handler)
            );
    }
}
