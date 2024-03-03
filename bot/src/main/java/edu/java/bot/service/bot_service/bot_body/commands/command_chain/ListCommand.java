package edu.java.bot.service.bot_service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_service.bot_body.commands.Command;
import edu.java.bot.service.bot_service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ListCommand extends AbstractCommand {
    private static final String NOTHING_TRACK = "No links are tracked.";

    public ListCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message, Command next) {
        super(inMemoryDataBase, message, next);
    }

    public ListCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message) {
        super(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public ListCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        if (notValid()) {
            return nextCommand.applyCommand();
        }

        int id = message.chat().id().intValue();

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
    protected boolean notValid() {
        return messageTextNull() || !message.text().equals("/list");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/list - list of tracked links";
    }

    private boolean nothingTrack() {
        int id = message.chat().id().intValue();

        return !(inMemoryDataBase.dataBase().containsKey(id)
            && !inMemoryDataBase.dataBase().get(id).isEmpty());
    }
}
