package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class EmptyCommand extends AbstractCommand {
    private static final String WRONG_COMMAND = "Wrong command.";

    public EmptyCommand(Message message) {
        super(message, null);
    }

    @Override
    protected boolean valid() {
        return false;
    }

    @Contract(" -> new") @Override
    public @NotNull CommandComplete applyCommand() {
        return new CommandComplete(WRONG_COMMAND, message.chat().id());
    }
}
