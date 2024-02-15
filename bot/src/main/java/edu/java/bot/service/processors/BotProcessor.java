package edu.java.bot.service.processors;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.botBody.telegramBots.Bot;
import edu.java.bot.service.botBody.telegramBots.LinkTrackerBot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BotProcessor implements Processor {
    private final Bot bot;

    @Autowired
    public BotProcessor(ApplicationConfig applicationConfig) {
        this.bot = new LinkTrackerBot(applicationConfig);
    }

    @PostConstruct
    @Override
    public void process() {
        bot.start();
    }
}
