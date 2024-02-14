package edu.java.bot.service.botBody.commands;

import com.pengrad.telegrambot.model.Message;

public class EmptyCommand implements Command {
    private static final String WRONG_COMMAND = "Wrong command. Try again or use /help.";
    private final Message message;

    public EmptyCommand(Message message) {
        this.message = message;
    }

    @Override
    public CommandComplete applyCommand() {
        return new CommandComplete(WRONG_COMMAND, message.chat().id());
    }
}
