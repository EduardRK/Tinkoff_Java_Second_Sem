package edu.java.bot.service.handlers;

import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseHandler<T> implements Handler<T> {
    protected static final ExecutorService EXECUTOR_SERVICE = Executors.newVirtualThreadPerTaskExecutor();
    protected final TelegramBotComponent telegramBotComponent;
    protected final ScrapperClient scrapperClient;

    protected BaseHandler(TelegramBotComponent telegramBotComponent, ScrapperClient scrapperClient) {
        this.telegramBotComponent = telegramBotComponent;
        this.scrapperClient = scrapperClient;
    }
}
