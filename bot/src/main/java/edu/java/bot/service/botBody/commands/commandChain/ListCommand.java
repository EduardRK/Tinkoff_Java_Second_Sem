package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.commands.EmptyCommand;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;

public final class ListCommand extends AbstractCommand {
    private static final String NOTHING_TRACK = "No links are tracked.";

    public ListCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message, Command next) {
        super(inMemoryDataBase, message, next);
    }

    public ListCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        super(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public ListCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return nextCommand.applyCommand();
        }

        long id = message.chat().id();

        if (nothingTrack()) {
            return new CommandComplete(NOTHING_TRACK, id);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Link link : inMemoryDataBase.dataBase().get(id)) {
            stringBuilder.append(link.toString()).append(System.lineSeparator());
        }

        return new CommandComplete(stringBuilder.toString(), id);
    }

    @Override
    protected boolean valid() {
        return messageTextNotNull() && message.text().equals("/list");
    }

    @Override
    public String toString() {
        return "/list - list of tracked links";
    }

    private boolean nothingTrack() {
        long id = message.chat().id();
        return !(inMemoryDataBase.dataBase().containsKey(id)
            && !inMemoryDataBase.dataBase().get(id).isEmpty());
    }
}
