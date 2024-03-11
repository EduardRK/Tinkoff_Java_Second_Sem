package edu.java.bot.service.processors;

import edu.java.bot.service.telegram_bot.TrackerBot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BotProcessor implements Processor {
    private final TrackerBot trackerBot;

    @Autowired
    public BotProcessor(TrackerBot trackerBot) {
        this.trackerBot = trackerBot;
    }

    @PostConstruct
    @Override
    public void process() {
        trackerBot.start();
    }
}
