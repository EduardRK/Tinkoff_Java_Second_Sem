package edu.java.bot.service.bot_service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.bot_service.bot_body.commands.Command;
import edu.java.bot.service.bot_service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import org.jetbrains.annotations.NotNull;

public final class CommandChain implements Command {
    private final Command chain;

    public CommandChain(Command chain) {
        this.chain = chain;
    }

    public static @NotNull CommandChain defaultChain(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        UntrackCommand untrackCommand = new UntrackCommand(inMemoryDataBase, message);
        TrackCommand trackCommand = new TrackCommand(inMemoryDataBase, message, untrackCommand);
        ListCommand listCommand = new ListCommand(inMemoryDataBase, message, trackCommand);
        HelpCommand helpCommand = new HelpCommand(inMemoryDataBase, message, listCommand);
        StartCommand startCommand = new StartCommand(inMemoryDataBase, message, helpCommand);

        return new CommandChain(startCommand);
    }

    public static @NotNull CommandChain defaultChain(InMemoryDataBase<Long, Link> inMemoryDataBase, Update update) {
        return defaultChain(inMemoryDataBase, update.message());
    }

    @Override
    public CommandComplete applyCommand() {
        return chain.applyCommand();
    }
}
