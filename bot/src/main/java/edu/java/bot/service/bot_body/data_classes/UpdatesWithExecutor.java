package edu.java.bot.service.bot_body.data_classes;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public record UpdatesWithExecutor(TelegramBot executor, List<? extends Update> updates) {
}
