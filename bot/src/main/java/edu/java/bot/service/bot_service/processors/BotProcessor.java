package edu.java.bot.service.bot_service.processors;

import edu.java.bot.service.bot_service.telegram_bot.Bot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BotProcessor implements Processor {
    private final Bot bot;

    @Autowired
    public BotProcessor(Bot bot) {
        this.bot = bot;
    }

    @PostConstruct
    @Override
    public void process() {
        bot.start();
    }
}
