package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.commands.EmptyCommand;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        return "/help - print all commands";
    }
}
