package edu.java.bot.service.botBody.commands;

import com.pengrad.telegrambot.model.Message;

public record CommandComplete(String message, long id) {
    public CommandComplete(Message message) {
        this(message.text(), message.chat().id());
    }
}
