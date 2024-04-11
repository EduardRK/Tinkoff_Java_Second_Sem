package edu.java.bot.service.telegram_bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.handlers.Handler;
import edu.java.bot.service.handlers.UpdateHandler;
import java.util.List;
import lombok.SneakyThrows;

public final class CommandUpdateListener implements UpdatesListener {
    private final Handler<List<Update>> handler;

    public CommandUpdateListener(UpdateHandler handler) {
        this.handler = handler;
    }

    @Override
    public int process(List<Update> updates) {
        handler.put(updates);
        return CONFIRMED_UPDATES_ALL;
    }
}
