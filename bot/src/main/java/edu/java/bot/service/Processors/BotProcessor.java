package edu.java.bot.service.Processors;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.BotBody.TelegramBots.Bot;
import edu.java.bot.service.BotBody.TelegramBots.NotificationsCollectorBot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BotProcessor implements Processor {
    private final Bot bot;

    @Autowired
    public BotProcessor(ApplicationConfig applicationConfig) {
        this.bot = new NotificationsCollectorBot(applicationConfig);
    }

    @PostConstruct
    @Override
    public void process() throws Exception {
        bot.start();
    }
}
