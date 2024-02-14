package edu.java.bot.service.botBody.telegramBots;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.botBody.dataClasses.UpdatesWithExecutor;
import edu.java.bot.service.botBody.handlers.Handler;
import edu.java.bot.service.botBody.handlers.UpdateHandler;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

public final class NotificationsCollectorBot implements Bot {
    private static final Handler<UpdatesWithExecutor> HANDLER = new UpdateHandler();
    private final TelegramBot telegramBot;

    @Autowired
    public NotificationsCollectorBot(@NotNull ApplicationConfig applicationConfig) {
        this.telegramBot = new TelegramBot.Builder(applicationConfig.telegramToken()).debug().build();
    }

    @Override
    public int process(@NotNull List<Update> updates) {
        HANDLER.put(new UpdatesWithExecutor(telegramBot, updates));
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
    }

    @Override
    public void start() {
        telegramBot.setUpdatesListener(this);
    }
}
