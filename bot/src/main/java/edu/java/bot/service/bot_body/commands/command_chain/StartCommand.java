package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.bot_body.data_classes.Link;
import edu.java.bot.service.data_base.InMemoryDataBase;
import java.util.HashSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
            return new UserRegistrationCheckCommand(inMemoryDataBase, message, nextCommand).applyCommand();
        }

        long id = message.chat().id();

        if (inMemoryDataBase.dataBase().containsKey(id)) {
            return new CommandComplete(ALREADY_REGISTRATION, id);
        }

        inMemoryDataBase.dataBase().put(id, new HashSet<>());
        return new CommandComplete(USER_REGISTERED, id);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/start - user registration";
    }

    private static final class UserRegistrationCheckCommand extends AbstractCommand {
        private static final String USER_NOT_REGISTER = "You are not registered. Use the command /start.";

        UserRegistrationCheckCommand(
            InMemoryDataBase<Long, Link> inMemoryDataBase,
            Message message,
            Command command
        ) {
            super(inMemoryDataBase, message, command);
        }

        @Override
        public CommandComplete applyCommand() {
            if (notValid()) {
                return new CommandComplete(
                    USER_NOT_REGISTER,
                    message.chat().id()
                );
            }

            return nextCommand.applyCommand();
        }

        @Override
        protected boolean notValid() {
            return !inMemoryDataBase.dataBase().containsKey(message.chat().id());
        }
    }
}
