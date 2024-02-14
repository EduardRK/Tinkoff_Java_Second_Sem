package edu.java.bot.service.botBody.dataClasses;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public record UpdatesWithExecutor(TelegramBot executor, List<? extends Update> updates) {
}
