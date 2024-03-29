package edu.java.bot.service.telegram_bot;

import edu.java.bot.service.handlers.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LinkTrackerBot implements TrackerBot {
    private final TelegramBotComponent telegramBotComponent;
    private final UpdateHandler handler;

    @Autowired
    public LinkTrackerBot(
        TelegramBotComponent telegramBotComponent,
        UpdateHandler handler
    ) {
        this.telegramBotComponent = telegramBotComponent;
        this.handler = handler;
    }

    @Override
    public void close() {
        telegramBotComponent.telegramBot()
            .removeGetUpdatesListener();
    }

    @Override
    public void start() {
        telegramBotComponent.telegramBot()
            .setUpdatesListener(
                new CommandUpdateListener(handler)
            );
    }
}
