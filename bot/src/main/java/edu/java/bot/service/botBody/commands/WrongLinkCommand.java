package edu.java.bot.service.botBody.commands;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WrongLinkCommand implements Command {
    private static final String WRONG_LINK = "Wrong link. Try again.";
    private final Message message;

    public WrongLinkCommand(Message message) {
        this.message = message;
    }

    @Contract(" -> new")
    @Override
    public @NotNull CommandComplete applyCommand() {
        return new CommandComplete(WRONG_LINK, message.chat().id());
    }
}
