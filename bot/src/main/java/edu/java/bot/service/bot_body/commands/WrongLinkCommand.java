package edu.java.bot.service.bot_body.commands;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.NotNull;

public final class WrongLinkCommand implements Command {
    private static final String WRONG_LINK = "Wrong link. Try again.";

    public WrongLinkCommand() {
    }

    @Override
    public @NotNull CommandComplete applyCommand(Message message) {
        return new CommandComplete(WRONG_LINK, message.chat().id());
    }
}
