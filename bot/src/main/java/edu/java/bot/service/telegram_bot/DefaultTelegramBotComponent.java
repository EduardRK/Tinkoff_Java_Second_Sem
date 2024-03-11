package edu.java.bot.service.telegram_bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class DefaultTelegramBotComponent implements TelegramBotComponent {
    private final TelegramBot telegramBot;

    @Autowired
    public DefaultTelegramBotComponent(ApplicationConfig applicationConfig) {
        this.telegramBot = new TelegramBot.Builder(applicationConfig.telegramToken()).debug().build();
    }

    @Override
    public TelegramBot telegramBot() {
        return telegramBot;
    }
}
