package edu.java.bot.service.BotBody.Commands.Completes;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.NotNull;

public record CommandComplete(String message, long id, boolean nextExpected) {
    public CommandComplete(String message, long id) {
        this(message, id, false);
    }

    public CommandComplete(@NotNull Message message, boolean nextExpected) {
        this(message.text(), message.chat().id(), nextExpected);
    }

    public CommandComplete(Message message) {
        this(message, false);
    }
}
