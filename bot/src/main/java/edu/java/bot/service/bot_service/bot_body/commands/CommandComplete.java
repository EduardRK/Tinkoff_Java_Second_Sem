package edu.java.bot.service.bot_service.bot_body.commands;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.NotNull;

public record CommandComplete(String message, int id) {
    public CommandComplete(@NotNull Message message) {
        this(message.text(), message.chat().id().intValue());
    }
}
