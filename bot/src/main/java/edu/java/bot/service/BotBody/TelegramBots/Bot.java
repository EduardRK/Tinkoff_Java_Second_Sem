package edu.java.bot.service.BotBody.TelegramBots;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.BotBody.Messages.MessageResponse;

public interface Bot extends AutoCloseable, UpdatesListener {
    void start();

    MessageResponse sendMessage(SendMessage sendMessage);
}
