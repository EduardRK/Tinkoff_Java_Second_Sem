package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WrongLinkCommand extends AbstractCommand {
    private static final String WRONG_LINK = "Wrong link.";

    public WrongLinkCommand(Message message) {
        super(message, null);
    }

    @Override
    protected boolean valid() {
        return false;
    }

    @Contract(" -> new") @Override
    public @NotNull CommandComplete applyCommand() {
        return new CommandComplete(WRONG_LINK, message.chat().id());
    }
}
