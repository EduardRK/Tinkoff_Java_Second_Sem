package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;

public final class HelpCommand extends AbstractCommand {
    private static final String COMMANDS = """
        /help - all commands
        /start - user registration
        /list - list of tracked links
        /track - start tracking link
        /untrack - stop tracking link
        """;

    public HelpCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public HelpCommand(Message message) {
        this(message, new EmptyCommand(message));
    }

    @Override
    protected boolean valid() {
        return notNull() && message.text().equals("/help");
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return next.applyCommand();
        } else {
            return new CommandComplete(COMMANDS, message.chat().id());
        }
    }
}
