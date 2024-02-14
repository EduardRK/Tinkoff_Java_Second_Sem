package edu.java.bot.service.botBody.commands;

import com.pengrad.telegrambot.model.Message;

public class WrongLinkCommand implements Command {
    private static final String WRONG_LINK = "Wrong link. Try again.";
    private final Message message;

    public WrongLinkCommand(Message message) {
        this.message = message;
    }

    @Override
    public CommandComplete applyCommand() {
        return new CommandComplete(WRONG_LINK, message.chat().id());
    }
}
