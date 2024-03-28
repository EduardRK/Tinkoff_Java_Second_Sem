package edu.java.bot.service.bot_body.commands;

import com.pengrad.telegrambot.model.Message;
import org.jetbrains.annotations.NotNull;

public final class EmptyCommand implements Command {
    private static final String WRONG_COMMAND = "Wrong command. Try again or use /help.";

    public EmptyCommand() {
    }

    @Override
    public @NotNull CommandComplete applyCommand(Message message) {
        return new CommandComplete(WRONG_COMMAND, message.chat().id());
    }
}
