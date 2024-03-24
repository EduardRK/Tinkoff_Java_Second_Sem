package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;
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

    public HelpCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public HelpCommand(ScrapperClient scrapperClient) {
        this(scrapperClient, new EmptyCommand());
    }

    public HelpCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractCommand command : COMMAND_LIST) {
            stringBuilder.append(command.toString()).append(System.lineSeparator());
        }

        return new CommandComplete(stringBuilder.toString(), message.chat().id());
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/help");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/help - print all commands";
    }
}
