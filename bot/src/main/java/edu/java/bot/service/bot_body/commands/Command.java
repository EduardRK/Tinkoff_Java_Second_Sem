package edu.java.bot.service.bot_body.commands;

import com.pengrad.telegrambot.model.Message;

public interface Command {
    CommandComplete applyCommand(Message message);
}
