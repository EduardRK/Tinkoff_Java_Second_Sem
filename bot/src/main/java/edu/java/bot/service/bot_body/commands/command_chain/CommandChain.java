package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.data_classes.Link;
import edu.java.bot.service.data_base.InMemoryDataBase;
import org.jetbrains.annotations.NotNull;

public final class CommandChain implements Command {
    private final Command chain;

    public CommandChain(Command chain) {
        this.chain = chain;
    }

    public CommandChain(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        this(
            new StartCommand(
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
        );
    }

    public CommandChain(InMemoryDataBase<Long, Link> inMemoryDataBase, @NotNull Update update) {
        this(inMemoryDataBase, update.message());
    }

//    public static CommandChain newChain(
//        Update update,
//        AbstractCommand... commands
//    ) {
//        return newChain(update.message(), commands);
//    }
//
//    public static CommandChain newChain(
//        Message message,
//        AbstractCommand... commands
//    ) {
//        return newChain(message, List.of(commands));
//    }
//
//    public static CommandChain newChain(
//        Update update,
//        List<? extends AbstractCommand> commands
//    ) {
//        return newChain(update.message(), commands);
//    }
//
//    public static CommandChain newChain(
//        Message message,
//        List<? extends AbstractCommand> commands
//    ) {
//        AbstractCommand chain = commands.getFirst();
//        AbstractCommand nextElement;
//
//        for (int i = 1; i < commands.size(); ++i) {
//        }
//
//        previousCommand.linkCommand(new EmptyCommand(message));
//        return new CommandChain(previousCommand);
//    }

    @Override
    public CommandComplete applyCommand() {
        return chain.applyCommand();
    }
}
