package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;

public final class UserRegistrationCheckCommand extends AbstractCommand {
    private static final String USER_NOT_REGISTER = "You are not registered. Use the command /start.";

    public UserRegistrationCheckCommand(
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
