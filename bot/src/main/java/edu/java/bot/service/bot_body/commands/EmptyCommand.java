package edu.java.bot.service.bot_body.commands;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class EmptyCommand implements Command {
    private static final String WRONG_COMMAND = "Wrong command. Try again or use /help.";
    private final Message message;

    public EmptyCommand(Message message) {
        this.message = message;
    }

    @Contract(" -> new")
    @Override
    public @NotNull CommandComplete applyCommand() {
        return new CommandComplete(WRONG_COMMAND, message.chat().id());
    }
}
