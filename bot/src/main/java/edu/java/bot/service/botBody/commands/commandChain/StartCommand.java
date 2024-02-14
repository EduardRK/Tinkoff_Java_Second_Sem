package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.commands.EmptyCommand;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import java.util.HashSet;

public final class StartCommand extends AbstractCommand {
    private static final String ALREADY_REGISTRATION = "The user is already registered.";
    private static final String USER_REGISTERED = "The user has been successfully registered.";

    public StartCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message, Command next) {
        super(inMemoryDataBase, message, next);
    }

    public StartCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        this(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public StartCommand() {
        this(null, null);
    }

    @Override
    protected boolean notValid() {
        return messageTextNull() || !message.text().equals("/start");
    }

    @Override
    public CommandComplete applyCommand() {
        if (notValid()) {
            return nextCommand.applyCommand();
        }

        long id = message.chat().id();

        if (inMemoryDataBase.dataBase().containsKey(id)) {
            return new CommandComplete(ALREADY_REGISTRATION, id);
        }

        inMemoryDataBase.dataBase().put(id, new HashSet<>());
        return new CommandComplete(USER_REGISTERED, id);
    }

    @Override
    public String toString() {
        return "/start - user registration";
    }
}
