package edu.java.bot.service.bot_service.handlers;

import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryIdLinkDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import edu.java.bot.service.bot_service.telegram_bot.DefaultTelegramBotComponent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseHandler<T> implements Handler<T> {
    protected static final ExecutorService EXECUTOR_SERVICE = Executors.newVirtualThreadPerTaskExecutor();
    protected static final InMemoryDataBase<Long, Link> DATA_BASE = new InMemoryIdLinkDataBase();
    protected final DefaultTelegramBotComponent defaultTelegramBotComponent;

    protected BaseHandler(DefaultTelegramBotComponent defaultTelegramBotComponent) {
        this.defaultTelegramBotComponent = defaultTelegramBotComponent;
    }
}
