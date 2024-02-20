package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.bot_body.data_classes.Link;
import edu.java.bot.service.data_base.InMemoryDataBase;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class HelpCommand extends AbstractCommand {
    private final static List<AbstractCommand> COMMAND_LIST = new ArrayList<>(
        List.of(
            new StartCommand(),
            new HelpCommand(),
            new ListCommand(),
            new TrackCommand(),
            new UntrackCommand()
        )
    );

    public HelpCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message, Command next) {
        super(inMemoryDataBase, message, next);
    }

    public HelpCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        this(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public HelpCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        if (notValid()) {
            return nextCommand.applyCommand();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractCommand command : COMMAND_LIST) {
            stringBuilder.append(command.toString()).append(System.lineSeparator());
        }

        return new CommandComplete(stringBuilder.toString(), message.chat().id());
    }

    @Override
    protected boolean notValid() {
        return messageTextNull() || !message.text().equals("/help");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/help - print all commands";
    }
}
