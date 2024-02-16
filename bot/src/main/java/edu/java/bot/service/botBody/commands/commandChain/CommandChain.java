package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import org.jetbrains.annotations.NotNull;

public class CommandChain implements Command {
    private final Command chain;

    public CommandChain(Command chain) {
        this.chain = chain;
    }

    public CommandChain(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        this(new StartCommand(
                inMemoryDataBase,
                message,
                new UserRegistrationCheckCommand(
                    inMemoryDataBase,
                    message,
                    new HelpCommand(
                        inMemoryDataBase,
                        message,
                        new ListCommand(
                            inMemoryDataBase,
                            message,
                            new TrackCommand(
                                inMemoryDataBase,
                                message,
                                new UntrackCommand(
                                    inMemoryDataBase,
                                    message
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    public CommandChain(InMemoryDataBase<Long, Link> inMemoryDataBase, @NotNull Update update) {
        this(inMemoryDataBase, update.message());
    }

    @Override
    public CommandComplete applyCommand() {
        return chain.applyCommand();
    }
}
