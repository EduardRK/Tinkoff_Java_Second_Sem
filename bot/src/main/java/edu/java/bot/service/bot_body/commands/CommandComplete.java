package edu.java.bot.service.bot_body.commands;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.NotNull;

public record CommandComplete(String message, long id) {
    public CommandComplete(@NotNull Message message) {
        this(message.text(), message.chat().id());
    }
}
