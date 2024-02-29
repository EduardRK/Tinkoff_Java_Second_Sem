package edu.java.bot.service.bot_service.telegram_bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
public final class TelegramBotComponent {
    private final TelegramBot telegramBot;

    public TelegramBotComponent(ApplicationConfig applicationConfig) {
        this.telegramBot = new TelegramBot.Builder("6466351131:AAGICxkGHk7xYMy8cjvjM9r_3AtFfhz5cVI").debug().build();
    }

    public TelegramBot telegramBot() {
        return telegramBot;
    }
}
