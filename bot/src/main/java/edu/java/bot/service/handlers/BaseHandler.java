package edu.java.bot.service.handlers;

import edu.java.bot.domain.InMemoryDataBase;
import edu.java.bot.domain.InMemoryIdLinkDataBase;
import edu.java.bot.service.bot_body.data_classes.Link;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseHandler<T> implements Handler<T> {
    protected static final ExecutorService EXECUTOR_SERVICE = Executors.newVirtualThreadPerTaskExecutor();
    protected static final InMemoryDataBase<Long, Link> DATA_BASE = new InMemoryIdLinkDataBase();
    protected final TelegramBotComponent telegramBotComponent;

    protected BaseHandler(TelegramBotComponent telegramBotComponent) {
        this.telegramBotComponent = telegramBotComponent;
    }
}
